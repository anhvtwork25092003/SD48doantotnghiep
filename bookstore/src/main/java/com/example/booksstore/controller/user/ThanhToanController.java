package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.PhuongThucThanhToan;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.PhuongThucThanhToanRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RequestMapping("/thanh-toan")
@Controller
public class ThanhToanController {

    @Autowired
    PhuongThucThanhToanRepo phuongThucThanhToanRepo;

    @Autowired
    IKhachHangRepository iKhachHangRepository;

    @Autowired
    IDonHangRepo iDonHangRepo;

    // chuyển hướng trang thanh toán
    @GetMapping("/xac-nhan-thanh-toan")
    public String xacNhanThanhToan(HttpSession session,
                                   @RequestParam(value = "SachDaChonDeMua", required = false) List<GioHangChiTiet> gioHangChiTietListDaChon,
                                   String idGioHang,
                                   Model model) {
        // xác minh đăng nhập

        model.addAttribute("phuongthucThanhToans", this.phuongThucThanhToanRepo.findAll());

        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        // chưa đăng nhập: chuyển đến trang chwua đăng nhập
        if (khachHangDangNhap == null) {
            // lấy giỏ hàng và list giỏ hàng chi tiết từ sesion
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTietListDaChon);
            return "/user/ThanhToanChuaDangNhap";
        } else {
            KhachHang khachHangHienThi = this.iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get();
            model.addAttribute("khachHangDangNhap", khachHangHienThi);
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTietListDaChon);
            return "/user/ThanhToanDaDangNhap";
        }

    }

    @GetMapping("/xac-nhan-len-don")
    public String xacNhanLenDon(HttpSession session,
                                @RequestParam(value = "danhSachChonMua", required = false) List<GioHangChiTiet> gioHangChiTietListForPay,
                                @RequestParam(value = "phuongThucThanhToanMaKhachHangChon", required = false) PhuongThucThanhToan phuongThucThanhToan,
                                @RequestParam(value = "tenNguoiNhan", required = false) String tenNguoiNhan,
                                @RequestParam(value = "soDienThoaiNhanHang", required = false) String soDienThoaiNhanHang,
                                @RequestParam(value = "tinhThanhPho", required = false) String tinhThanhPho,
                                @RequestParam(value = "huyenQuan", required = false) String huyenQuan,
                                @RequestParam(value = "xaPhuong", required = false) String xaPhuong,
                                @RequestParam(value = "diaChiCuThe", required = false) String diaChiCuThe


    ) {
// xác minh đăng nhậpS
        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        System.out.println(tinhThanhPho + " " + xaPhuong + " " + huyenQuan);
        if (khachHangDangNhap == null) {
            // chưa đăng nhạp
            // giảm trừ list trong sesion  va tạo đơn hàng với nhân viên trống, trạng thá
            // phân biệt phương thức thanh toán
            // nếu chọn thanh toán trực tuyến thì sẽ chuyển đến vn pay
            // nếu chọn thanh toán bằng tiền mặt => đặt hàng thành công, chuyển thông tin đơn lên admin


        } else {
            // đã đăng nhập rồi nè
            // kiểm tra phương thức  thanh taons nè
            if (phuongThucThanhToan.getIdPhuongThucThanhToan() == 1) {
                // thanh toán với vnpay
                // xử lý sau

            } else {
                // thanh toán tienf mặt khi nhận  hàng
                // => tạo đơn nào
                // đầu tiên lấy ra ngày tạo đơn
                Date currentDate = new Date();
                // lấy ra tổng tiền hàng nè
                // duyệt list gioHangChiTietListForPay( giohangchitiet) => lấy sách => lấy tiền khuyến mãi

                // b1: lấy tổng tiền hàng gốc
                BigDecimal tongTienHangGoc = BigDecimal.ZERO;
                for (GioHangChiTiet gioHangChiTiet : gioHangChiTietListForPay) {
                    BigDecimal giaBan = gioHangChiTiet.getSach().getGiaBan();
                    int soLuong = gioHangChiTiet.getSoLuong();
                    BigDecimal thanhTien = giaBan.multiply(BigDecimal.valueOf(soLuong));
                    tongTienHangGoc = tongTienHangGoc.add(thanhTien);
                }
                BigDecimal tongTienHangKhuyenMai = BigDecimal.ZERO;

                // b2: lấy tổng tiền khuyến mãi
                for (GioHangChiTiet gioHangChiTiet : gioHangChiTietListForPay) {
                    //mỗi giỏ hàng chi tiết đi cùng  1 sách => đi cùng nhiều khuyến mãi => duyệt list lấy ra số phần
                    // trăm giảm được áp dụng => sau đó tiến hành giảm trừ
                    for (KhuyenMai khuyenMai : gioHangChiTiet.getSach().getKhuyenMais()) {
                        if (khuyenMai.getTrangThai() == 1) {
                            // khuyến mãi đang được áp dụng!
                            // lây sra số phần trăm giảm giá
                            double sophangiam = (khuyenMai.getSoPhanTramGiamGia()) / 100;
                            BigDecimal thanhTienKhuyenMai = gioHangChiTiet.getSach().getGiaBan().multiply(BigDecimal.valueOf(sophangiam));
                            BigDecimal.ZERO.add(thanhTienKhuyenMai);
                            break;
                        } else {
                            tongTienHangKhuyenMai = BigDecimal.ZERO;
                        }
                    }
                }


                // phí vận chuyển fixx cứng 50 000
                BigDecimal phiVanChuyen = new BigDecimal(50000);


                // tổng tiền cần thanh toán: tiền hàng gốc - trừ tiền khuyến amix + tiền vận chuyển 50 000
                BigDecimal tongTienCanThanhToan = tongTienHangGoc.subtract(tongTienHangKhuyenMai).add(phiVanChuyen);

                // trạng thái sẽ là 0 = > chờ xác nhận lên đơn nè
                int trangThai = 0;// chua xác nhạna
                // ghi chú đơn hàng của khách hàng viết vào !
                // ghi chú trống hihih
                // ghi chú lý do hủy đơn hàng

                // khách hàng  = > lấy từ  sesion cho nhanh
                KhachHang khachHangMoiDeLuu = this.iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get();
                DonHang donHang = DonHang.builder()
                        .khachHang(khachHangMoiDeLuu)
                        .ghiChuLyDoDonHang("")
                        .tongTienHangGoc(tongTienHangGoc)
                        .ghiChuKhachHangui("")
                        .ngayTao(currentDate)
                        .phiVanChuyen(phiVanChuyen)
                        .phuongThucThanhToan(phuongThucThanhToan)
                        .tongTienKhuyenMai(tongTienHangKhuyenMai)
                        .tongTienCanThanhToan(tongTienCanThanhToan)
                        .trangThai(trangThai)
                        .build();

                // cuối cùng tiến hành lưu nè


            }
        }
        return "/user/pay";
    }
}
