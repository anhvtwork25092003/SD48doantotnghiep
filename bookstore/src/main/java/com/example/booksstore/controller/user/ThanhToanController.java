package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.PhuongThucThanhToan;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.ThongBao;
import com.example.booksstore.entities.ThongTinGiaoHang;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.GioHangChiTietReposutory;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.repository.IThongTinGiaoHangRepo;
import com.example.booksstore.repository.PhuongThucThanhToanRepo;
import com.example.booksstore.service.IDonHangService;
import com.example.booksstore.service.IThongBaoService;
import com.example.booksstore.service.ThongBaoKhachHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequestMapping("/thanh-toan")
@Controller
public class ThanhToanController {

    @Autowired
    IDonHangService iDonHangService;

    @Autowired
    ThongBaoKhachHangService thongBaoKhachHangService;
    @Autowired
    DonHangChiTietRepo donHangChiTietRepo;

    @Autowired
    IThongTinGiaoHangRepo iThongTinGiaoHangRepo;

    @Autowired
    PhuongThucThanhToanRepo phuongThucThanhToanRepo;

    @Autowired
    IKhachHangRepository iKhachHangRepository;
    @Autowired
    ISachRepository iSachRepository;

    @Autowired
    GioHangChiTietReposutory gioHangChiTietReposutory;

    @Autowired
    IDonHangRepo iDonHangRepo;
    @Autowired
    IThongBaoService iThongBaoService;

    public String formatCurrency(BigDecimal amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(amount);
    }

    // chuyển hướng trang thanh toán
    @GetMapping("/xac-nhan-thanh-toan")
    public String xacNhanThanhToan(HttpSession session,
                                   @RequestParam(value = "selectedValues", required = false) List<String> IdgioHangChiTietListDaChon,
                                   Model model) {
        // xác minh đăng nhập
        model.addAttribute("phuongthucThanhToans", this.phuongThucThanhToanRepo.findAll());

        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        // chưa đăng nhập: chuyển đến trang chwua đăng nhập
        if (khachHangDangNhap == null) {
            // lấy list giỏ hàng chi tiết chưa chọn -- toàn bộ giỏ hàng lưu ở sesion
            List<GioHangChiTiet> listSanPhamTrongGioHangTamThoi = (List<GioHangChiTiet>) session
                    .getAttribute("listSanPhamTrongGioHangTamThoi");
            if (listSanPhamTrongGioHangTamThoi == null) {
                listSanPhamTrongGioHangTamThoi = new ArrayList<>();
            }
            List<GioHangChiTiet> gioHangChiTietListDaChon = new ArrayList<>();
            for (GioHangChiTiet gioHangChiTiet : listSanPhamTrongGioHangTamThoi) {
                for (String idgh : IdgioHangChiTietListDaChon) {
                    if (gioHangChiTiet.getIdGioHangChiTiet() == Integer.parseInt(idgh)) {
                        // lấy thông tin mới nhất của sách
                        Sach sach = this.iSachRepository.findByIdSach(gioHangChiTiet.getSach().getIdSach());
                        gioHangChiTiet.setSach(sach);
                        gioHangChiTietListDaChon.add(gioHangChiTiet);
                    }
                }
            }
            // lấy giỏ hàng và list giỏ hàng chi tiết từ sesion
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTietListDaChon);
            session.setAttribute("danhSachSanPhamDeThanhToan", gioHangChiTietListDaChon);
            BigDecimal tinhTongTienDonHangDaTinhVanChuyen = iDonHangService.tinhTongTienDonHangDaTinhVanChuyen(gioHangChiTietListDaChon);
            model.addAttribute("tinhTongTienDonHangDaTinhVanChuyen", formatCurrency(tinhTongTienDonHangDaTinhVanChuyen));
            BigDecimal tinhTongTienDonHangChuaTinhVanChuyen = iDonHangService.tinhTongTienDonHangChuaTinhVanChuyen(gioHangChiTietListDaChon);
            model.addAttribute("tinhTongTienDonHangChuaTinhVanChuyen", formatCurrency(tinhTongTienDonHangChuaTinhVanChuyen));
            return "/user/ThanhToanChuaDangNhap";
        } else {
            List<GioHangChiTiet> gioHangChiTietListDaChon = new ArrayList<>();
            for (String gioHangChiTietId : IdgioHangChiTietListDaChon) {
                GioHangChiTiet gioHangChiTietforAddList = gioHangChiTietReposutory.findById(Integer.parseInt(gioHangChiTietId)).get();
                gioHangChiTietListDaChon.add(gioHangChiTietforAddList);
            }
            KhachHang khachHangHienThi = this.iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get();
            model.addAttribute("khachHangDangNhap", khachHangHienThi);
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTietListDaChon);
            session.setAttribute("danhSachSanPhamDeThanhToan", gioHangChiTietListDaChon);
            BigDecimal tinhTongTienDonHangDaTinhVanChuyen = iDonHangService.tinhTongTienDonHangDaTinhVanChuyen(gioHangChiTietListDaChon);
            model.addAttribute("tinhTongTienDonHangDaTinhVanChuyen", formatCurrency(tinhTongTienDonHangDaTinhVanChuyen));
            BigDecimal tinhTongTienDonHangChuaTinhVanChuyen = iDonHangService.tinhTongTienDonHangChuaTinhVanChuyen(gioHangChiTietListDaChon);
            model.addAttribute("tinhTongTienDonHangChuaTinhVanChuyen", formatCurrency(tinhTongTienDonHangChuaTinhVanChuyen));
            session.setAttribute("selectedValues", IdgioHangChiTietListDaChon);
            return "/user/ThanhToanDaDangNhap";
        }

    }

    @PostMapping("/xac-nhan-len-don")
    public String xacNhanLenDon(HttpSession session,
                                @RequestParam(value = "danhSachChonMua", required = false)
                                        List<GioHangChiTiet> gioHangChiTietListForPay,
                                @RequestParam(value = "phuongThucThanhToanMaKhachHangChon", required = false)
                                        PhuongThucThanhToan phuongThucThanhToan,
                                @RequestParam(value = "tenNguoiNhan", required = false)
                                        String tenNguoiNhan,
                                @RequestParam(value = "soDienThoaiNhanHang", required = false)
                                        String soDienThoaiNhanHang,
                                @RequestParam(value = "tinhThanhPho", required = false)
                                        String tinhThanhPho,
                                @RequestParam(value = "huyenQuan", required = false)
                                        String huyenQuan,
                                @RequestParam(value = "xaPhuong", required = false)
                                        String xaPhuong,
                                @RequestParam(value = "diaChiChu", required = false)
                                        String diaChiChu,
                                @RequestParam(value = "email", required = false)
                                        String email,
                                @RequestParam(value = "diaChiCuThe", required = false)
                                        String diaChiCuThe,
                                @RequestParam(value = "diaChiRadio", required = false) DiaChi diaChiKhachHang,
                                @RequestParam(value = "ghiChuKhachHangGui", required = false) String ghiChuKhachHangGui
    ) {
// xác minh đăng nhậpS
        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        // chưa đăng nhạp
        if (khachHangDangNhap == null) {
            // thanh toán vnpay
            if (phuongThucThanhToan.getIdPhuongThucThanhToan() == 1) {
                // thanh toán với vnpay
                // đưa toltal đi thanh toán, nếu total thanh toán thành công thì khởi chạy sau
                // lấy thông tin total => chuyển vnpay=> lấy trạng thái
                List<GioHangChiTiet> gioHangChiTietList = (List<GioHangChiTiet>) session.getAttribute("danhSachSanPhamDeThanhToan");
                List<DonHangChiTiet> donHangChiTietListVnpay = new ArrayList<>();
                BigDecimal tongTienhang = BigDecimal.ZERO;
                for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
                    //chuyển đổi giohang chi tiết = > đơn hàng chi tiết
                    DonHangChiTiet donHangChiTietDeThemVaoList = new DonHangChiTiet();
                    donHangChiTietDeThemVaoList.setSach(gioHangChiTiet.getSach());
                    // Số lượng
                    donHangChiTietDeThemVaoList.setSoLuong(gioHangChiTiet.getSoLuong());
                    // giá gốc
                    donHangChiTietDeThemVaoList.setGiaGoc(gioHangChiTiet.getSach().getGiaBan());

                    //phần trăm giả giá , lấy từ khuyens mãi
                    double soPhanTramGiamGia = 0.00;
                    for (KhuyenMai khuyenMai : gioHangChiTiet.getSach().getKhuyenMais()) {
                        if (khuyenMai.getTrangThai() == 1) {
                            // khuyến mãi đang được áp dụng!
                            System.out.println(khuyenMai.getSoPhanTramGiamGia());
                            // lây sra số phần trăm giảm giá
                            soPhanTramGiamGia = (double) ((khuyenMai.getSoPhanTramGiamGia()) / 100.0);
                            // khuyến mãi
                            donHangChiTietDeThemVaoList.setKhuyenMai(khuyenMai);
                            break;
                        }
                    }
                    donHangChiTietDeThemVaoList.setPhanTramGiam(soPhanTramGiamGia * 100);

                    //đơn giá thời điểm mua- sau khi giảm trừ
                    BigDecimal donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan();
                    System.out.println("don gia thoi diem mua: " + donGiathoiDiemMua);
                    if (soPhanTramGiamGia > 0.00) {
                        BigDecimal thanhTienKhuyenMai = gioHangChiTiet.getSach().getGiaBan().multiply(BigDecimal.valueOf(soPhanTramGiamGia));
                        donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan().subtract(thanhTienKhuyenMai);
                    }
                    donHangChiTietDeThemVaoList.setDonGiaThoiDiemMua(donGiathoiDiemMua);

                    // thanh tien
                    BigDecimal thanhTien = donGiathoiDiemMua.multiply(BigDecimal.valueOf(Double.valueOf(gioHangChiTiet.getSoLuong())));
                    donHangChiTietDeThemVaoList.setThanhTien(thanhTien);
                    tongTienhang = tongTienhang.add(thanhTien);

                    donHangChiTietListVnpay.add(donHangChiTietDeThemVaoList);
                }
                BigDecimal tienVanChuyen = new BigDecimal(50000);
                tongTienhang = tongTienhang.add(tienVanChuyen);
                session.setAttribute("sanphamdathanhtoanboivnpay", donHangChiTietListVnpay);
                // lưu các thông tin cần thiết vào sesion
                // 1 phuong thức thanh toán
                session.setAttribute("phuongThucThanhToan", phuongThucThanhToan);
                // gửi thêm thông tin để tạo đơn hàng, gửi các thông tin giao hàng được lưu ở
                session.setAttribute("phuongThucThanhToan", phuongThucThanhToan);
                session.setAttribute("tenNguoiNhanHangVnpay", tenNguoiNhan);
                session.setAttribute("soDienThoaiNhanHangVnpay", soDienThoaiNhanHang);
                session.setAttribute("tinhThanhPhoVnpay", tinhThanhPho);
                session.setAttribute("huyenQuanVnpay", huyenQuan);
                session.setAttribute("xaPhuongVnpay", xaPhuong);
                session.setAttribute("diaChiChuVnpay", diaChiChu);
                session.setAttribute("diaChiCuTheVnpay", diaChiCuThe);
                session.setAttribute("emailThanhToanChuaDangNhap", email);
                session.setAttribute("ghiChuKhachHangGui", ghiChuKhachHangGui);
                return "redirect:/vnpay/pay?total=" + tongTienhang;
            }
            // thanh toan tien mat
            else {
                // không thanh toán với vnpay
                // tao khách hàng mới
                Date currentDate = new Date();
                KhachHang khachHangVangLaimoi = KhachHang.builder()
                        .hoVaTen(tenNguoiNhan)
                        .ngayTaoTaiKhoan(currentDate)
                        .loaiKhachHang("0")
                        .build();
                KhachHang khachHang = iKhachHangRepository.save(khachHangVangLaimoi);
                List<GioHangChiTiet> gioHangChiTietList = (List<GioHangChiTiet>) session.getAttribute("danhSachSanPhamDeThanhToan");

                // thanh toán tienf mặt khi nhận  hàng
                DonHang donHang =
                        luuDonHang(phuongThucThanhToan,
                                khachHang,
                                tenNguoiNhan,
                                soDienThoaiNhanHang,
                                tinhThanhPho,
                                huyenQuan,
                                xaPhuong,
                                diaChiChu,
                                diaChiCuThe,
                                email,
                                ghiChuKhachHangGui,
                                gioHangChiTietList
                        );
                for (DonHangChiTiet donHangChiTiet : donHang.getChiTietDonHang()) {
                    this.iDonHangService.truSoLuongTonKho(donHangChiTiet);
                }
                System.out.println("tạo thành công đơn hàng có mã hóa đơn là: " + donHang.getMaDonHang());
                return "redirect:/vnpay/vnpayreturn?idDonHang=" + donHang.getIdDonHang();

            }

            // thanh toán đã đăng nhập
        } else {
            // đã đăng nhập rồi nè
            // kiểm tra phương thức  thanh taons nè
            if (phuongThucThanhToan.getIdPhuongThucThanhToan() == 1) {
                // thanh toán với vnpay
                // xử lý sau
                // đưa toltal đi thanh toán, nếu total thanh toán thành công thì khởi chạy sau
                // lấy thông tin total => chuyển vnpay=> lấy trạng thái
                List<GioHangChiTiet> gioHangChiTietList = (List<GioHangChiTiet>) session.getAttribute("danhSachSanPhamDeThanhToan");
                List<DonHangChiTiet> donHangChiTietListVnpay = new ArrayList<>();
                BigDecimal tongTienhang = BigDecimal.ZERO;
                for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
                    //chuyển đổi giohang chi tiết = > đơn hàng chi tiết
                    DonHangChiTiet donHangChiTietDeThemVaoList = new DonHangChiTiet();
                    donHangChiTietDeThemVaoList.setSach(gioHangChiTiet.getSach());
                    // Số lượng
                    donHangChiTietDeThemVaoList.setSoLuong(gioHangChiTiet.getSoLuong());
                    // giá gốc
                    donHangChiTietDeThemVaoList.setGiaGoc(gioHangChiTiet.getSach().getGiaBan());

                    //phần trăm giả giá , lấy từ khuyens mãi
                    double soPhanTramGiamGia = 0.00;
                    for (KhuyenMai khuyenMai : gioHangChiTiet.getSach().getKhuyenMais()) {
                        if (khuyenMai.getTrangThai() == 1) {
                            // khuyến mãi đang được áp dụng!
                            System.out.println(khuyenMai.getSoPhanTramGiamGia());
                            // lây sra số phần trăm giảm giá
                            soPhanTramGiamGia = (double) ((khuyenMai.getSoPhanTramGiamGia()) / 100.0);
                            // khuyến mãi
                            donHangChiTietDeThemVaoList.setKhuyenMai(khuyenMai);
                            break;
                        }
                    }
                    donHangChiTietDeThemVaoList.setPhanTramGiam(soPhanTramGiamGia * 100);

                    //đơn giá thời điểm mua- sau khi giảm trừ
                    BigDecimal donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan();
                    System.out.println("don gia thoi diem mua: " + donGiathoiDiemMua);
                    if (soPhanTramGiamGia > 0.00) {
                        BigDecimal thanhTienKhuyenMai = gioHangChiTiet.getSach().getGiaBan().multiply(BigDecimal.valueOf(soPhanTramGiamGia));
                        donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan().subtract(thanhTienKhuyenMai);
                    }
                    donHangChiTietDeThemVaoList.setDonGiaThoiDiemMua(donGiathoiDiemMua);

                    // thanh tien
                    BigDecimal thanhTien = donGiathoiDiemMua.multiply(BigDecimal.valueOf(Double.valueOf(gioHangChiTiet.getSoLuong())));
                    donHangChiTietDeThemVaoList.setThanhTien(thanhTien);
                    tongTienhang = tongTienhang.add(thanhTien);

                    donHangChiTietListVnpay.add(donHangChiTietDeThemVaoList);
                }
                BigDecimal tienVanChuyen = new BigDecimal(50000);
                tongTienhang = tongTienhang.add(tienVanChuyen);
                session.setAttribute("sanphamdathanhtoanboivnpay", donHangChiTietListVnpay);
                // lưu các thông tin cần thiết vào sesion
                // 1 phuong thức thanh toán
                session.setAttribute("phuongThucThanhToan", phuongThucThanhToan);
                session.setAttribute("diaChiKhachHang", diaChiKhachHang);
                session.setAttribute("diaChiCuTheVnpay", diaChiCuThe);

                return "redirect:/vnpay/pay?total=" + tongTienhang;
            } else {
                // không  thanh toán với vnpay
                KhachHang khachHang = iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get();
                List<GioHangChiTiet> gioHangChiTietList = (List<GioHangChiTiet>) session.getAttribute("danhSachSanPhamDeThanhToan");

                // thanh toán tienf mặt khi nhận  hàng
                DonHang donHang =
                        luuDonHang(phuongThucThanhToan,
                                khachHang,
                                diaChiKhachHang.getTenNguoiNhan(),
                                diaChiKhachHang.getSdtNguoiNhanHang(),
                                diaChiKhachHang.getTinhThanhPho(),
                                diaChiKhachHang.getHuyenQuan(),
                                diaChiKhachHang.getXaPhuong(),
                                diaChiKhachHang.getDiaChiChu(),
                                diaChiKhachHang.getDiaChiCuThe(),
                                khachHang.getEmail(),
                                ghiChuKhachHangGui,
                                gioHangChiTietList
                        );
                // trừ số lượng hàng tồn kho
                for (DonHangChiTiet donHangChiTiet : donHang.getChiTietDonHang()) {
                    this.iDonHangService.truSoLuongTonKho(donHangChiTiet);
                }
                System.out.println("tạo thành công đơn hàng có mã hóa đơn là: " + donHang.getMaDonHang());
                guiThongBao(donHang, khachHang);
                return "redirect:/vnpay/vnpayreturn?idDonHang=" + donHang.getIdDonHang();
            }
        }
    }


    public DonHang luuDonHang(
            PhuongThucThanhToan phuongThucThanhToan,
            KhachHang khachHang,
            String tenNguoiNhan,
            String sdt,
            String thanhPho,
            String quanHuyehn,
            String phuongXa,
            String diaChiChu,
            String diaChiCuThe,
            String emailGiaoHang,
            String ghiChuKhachHangGui,
            List<GioHangChiTiet> gioHangChiTietListForPay


    ) {
        // DON HANG
        // bước 1: tạo 1 đơn hàng trống trong cơ sở dữ liệu
        DonHang donhangForstep1 = DonHang.builder().build();
        DonHang donHangVuaKhoiTao = this.iDonHangRepo.save(donhangForstep1); // đơn hàng này đã có id và mã
        // thêm các thuoocjt ính cho đơn hàng

        // ngày tạo
        Date currentDateForCreateDonHang = new Date();
        donHangVuaKhoiTao.setNgayTao(currentDateForCreateDonHang);

        // ngày thanh toán + phuongthucthanhtoan + trangj thais thanh toan
        // nếu idphungthucthanhtoan là 0, không khởi chạy ngày thanh toán, nếu idphungthucthanhtoan là 0,
        // kh là 1 khởi tạo ngày thanh toán
        donHangVuaKhoiTao.setPhuongThucThanhToan(phuongThucThanhToan);
        if (phuongThucThanhToan.getIdPhuongThucThanhToan() == 1) {
            Date ngayThanhToanForCreateDonHang = new Date(); // thanh toan qua vnpay
            donHangVuaKhoiTao.setNgayThanhToan(ngayThanhToanForCreateDonHang);
            int trangThaiThanhToanForCreateDonHang = 1;
            donHangVuaKhoiTao.setTrangThaithanhtoan(trangThaiThanhToanForCreateDonHang);
        } else {
            // thanh toan tienf mat =>  trạng thái thanh toán là 0, ngày thanh toán trống
            int trangThaiThanhToanForCreateDonHang = 0;
            donHangVuaKhoiTao.setTrangThaithanhtoan(trangThaiThanhToanForCreateDonHang);
        }

        // phí vận chuyển- mặc định 50 k
        donHangVuaKhoiTao.setPhiVanChuyen(BigDecimal.valueOf(50000.00));

        // trạng thái => mặc định là 0
        donHangVuaKhoiTao.setTrangThai(0);

        // ghi chú khách hàng gửi => tạm thời bỏ qua

        // ghi chú lý do don hang => phục vụ cho việc trả ... => bỏ qua

        // khách hàng
        donHangVuaKhoiTao.setKhachHang(khachHang);

        //nhân viên => sẽ thêm ở giao diện admin => bỏ qua

        //THONG TIN GIAO HANG
        ThongTinGiaoHang thongTinGiaoHangForCreateDonHang =
                ThongTinGiaoHang.builder()
                        .tenNguoiNhan(tenNguoiNhan)
                        .sdt(sdt)
                        .thanhPho(thanhPho)
                        .quanHuyen(quanHuyehn)
                        .phuongXa(phuongXa)
                        .diaChiChu(diaChiChu)
                        .emailGiaoHang(emailGiaoHang)
                        .diaChiCuThe(diaChiCuThe)
                        .build();
        ThongTinGiaoHang thongTinGiaoHangMoiLuu = this.iThongTinGiaoHangRepo.save(thongTinGiaoHangForCreateDonHang);

        donHangVuaKhoiTao.setThongTinGiaoHang(thongTinGiaoHangMoiLuu);

        //CHI TIET DON HANG
        List<DonHangChiTiet> donHangChiTietList = new ArrayList<>();
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietListForPay) {
            //chuyển đổi giohang chi tiết = > đơn hàng chi tiết
            DonHangChiTiet donHangChiTietDeThemVaoList = new DonHangChiTiet();
            donHangChiTietDeThemVaoList.setDonHang(donHangVuaKhoiTao);
            // Số lượng
            donHangChiTietDeThemVaoList.setSoLuong(gioHangChiTiet.getSoLuong());

            // giá gốc
            donHangChiTietDeThemVaoList.setGiaGoc(gioHangChiTiet.getSach().getGiaBan());

            //phần trăm giả giá , lấy từ khuyens mãi
            double soPhanTramGiamGia = 0.00;
            for (KhuyenMai khuyenMai : gioHangChiTiet.getSach().getKhuyenMais()) {
                if (khuyenMai.getTrangThai() == 1) {
                    // khuyến mãi đang được áp dụng!
                    System.out.println(khuyenMai.getSoPhanTramGiamGia());

                    // lây sra số phần trăm giảm giá
                    soPhanTramGiamGia = (double) ((khuyenMai.getSoPhanTramGiamGia()) / 100.0);
                    System.out.println(soPhanTramGiamGia);
                    // khuyến mãi
                    donHangChiTietDeThemVaoList.setKhuyenMai(khuyenMai);
                    break;
                }
            }

            donHangChiTietDeThemVaoList.setPhanTramGiam(soPhanTramGiamGia * 100);
            //đơn giá thời điểm mua- sau khi giảm trừ
            BigDecimal donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan();
            if (soPhanTramGiamGia != 0.00) {
                BigDecimal thanhTienKhuyenMai = gioHangChiTiet.getSach().getGiaBan().multiply(BigDecimal.valueOf(soPhanTramGiamGia));
                donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan().subtract(thanhTienKhuyenMai);
            }
            donHangChiTietDeThemVaoList.setDonGiaThoiDiemMua(donGiathoiDiemMua);

            // thanh tien
            BigDecimal thanhTien = donGiathoiDiemMua.multiply(BigDecimal.valueOf(Double.valueOf(gioHangChiTiet.getSoLuong())));
            donHangChiTietDeThemVaoList.setThanhTien(thanhTien);

            // sachs
            donHangChiTietDeThemVaoList.setSach(gioHangChiTiet.getSach());


            DonHangChiTiet donHangChiTietMoiLuuVaoDB = this.donHangChiTietRepo.save(donHangChiTietDeThemVaoList);
            donHangChiTietList.add(donHangChiTietDeThemVaoList);
        }
        donHangVuaKhoiTao.setChiTietDonHang(donHangChiTietList);
        donHangVuaKhoiTao.setGhiChuKhachHangGui(ghiChuKhachHangGui);

        // tiến hành lưu lại đơn hàng
        DonHang donHangSauKhiLuu = this.iDonHangRepo.save(donHangVuaKhoiTao);
        return donHangSauKhiLuu;
    }

    public void guiEmail(String email,
                         String maHoaDon,
                         String soTien,
                         String tenNguoiNhan,
                         String phuongThucThanhToan,
                         String phuongThucvanChuyen,
                         String ngayThanhToan,
                         List<DonHangChiTiet> donHangChiTietListForEmail
    ) {

    }

    public void guiThongBao(DonHang donHang, KhachHang khachHang) {
        Date currDate = new Date();
        String noiDung = "Thông Báo về đơn hàng " + donHang.getMaDonHang() + "\n"
                + "Đơn hàng của bạn sẽ sớm được xử lý, cảm ơn đã mua hàng!";
        ThongBao thongBao = new ThongBao();
        thongBao.setNgayGui(currDate);
        thongBao.setNoiDung(noiDung);
        // lưu thông báo vào db

        ThongBao savedNotification = this.iThongBaoService.createNew(thongBao);
        // gọi hàm
        this.thongBaoKhachHangService.themThongBaoChoNguoiDung(khachHang.getIdKhachHang(), savedNotification);

    }
}
