package com.example.booksstore.controller.user;

import com.example.booksstore.config.Config;
import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.PhuongThucThanhToan;
import com.example.booksstore.entities.ThongTinGiaoHang;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.IThongTinGiaoHangRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


@Controller
@RequestMapping("/vnpay")
public class PaymentController {
    @Autowired
    IDonHangRepo iDonHangRepo;

    @Autowired
    DonHangChiTietRepo donHangChiTietRepo;

    @Autowired
    IThongTinGiaoHangRepo iThongTinGiaoHangRepo;
    @Autowired
    IKhachHangRepository iKhachHangRepository;

    @GetMapping("/vnpayreturn")
    public String vnpayReturn(@RequestParam("vnp_ResponseCode") String responseCode,
                              @RequestParam("vnp_TransactionNo") String transactionNo,
                              @RequestParam("vnp_TransactionStatus") String vnp_TransactionStatus,
                              @RequestParam("vnp_Amount") String amount,
                              // Thêm các tham số cần thiết khác từ callback URL
                              @RequestParam("vnp_SecureHash") String secureHash,
                              HttpSession session, Model model) {
        System.out.println(vnp_TransactionStatus);
        if (Integer.parseInt(vnp_TransactionStatus) == 00) {
            System.out.println("thanh toan thành công!");
            List<DonHangChiTiet> DonHangChiTietDaDuocVnPayThanhToan = (List<DonHangChiTiet>) session.getAttribute("sanphamdathanhtoanboivnpay");
            PhuongThucThanhToan phuongThucThanhToan = (PhuongThucThanhToan) session.getAttribute("phuongThucThanhToan");
            DiaChi diaChiKhachHang = (DiaChi) session.getAttribute("diaChiKhachHang");
            KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
            KhachHang khachHang = null;
            if (khachHangDangNhap != null) {
                // đã đăng nhập, lấy thông tin từ db
                khachHang = iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get();

            } else {
                // chưa đăng nhập, lấy thông tin được truyền qua
                String tenNguoiNhanHang = (String) session.getAttribute("tenNguoiNhanHangVnpay");
                String soDienThoaiNhanHangVnpay = (String) session.getAttribute("soDienThoaiNhanHangVnpay");
                String tinhThanhPhoVnpay = (String) session.getAttribute("tinhThanhPhoVnpay");
                String huyenQuanVnpay = (String) session.getAttribute("huyenQuanVnpay");
                String xaPhuongVnpay = (String) session.getAttribute("xaPhuongVnpay");
                String diaChiCuTheVnpay = (String) session.getAttribute("diaChiCuTheVnpay");

                KhachHang khachHang1 = KhachHang.builder()
                        .loaiKhachHang("Khách Vãng Lai")
                        .hoVaTen(tenNguoiNhanHang)
                        .sdt(soDienThoaiNhanHangVnpay)
                        .

            }
            DonHang donHang =
                    luuDonHang(phuongThucThanhToan,
                            khachHang,
                            diaChiKhachHang.getTenNguoiNhan(),
                            diaChiKhachHang.getSdtNguoiNhanHang(),
                            diaChiKhachHang.getTinhThanhPho(),
                            diaChiKhachHang.getHuyenQuan(),
                            diaChiKhachHang.getXaPhuong(),
                            diaChiKhachHang.getDiaChiCuThe(),
                            DonHangChiTietDaDuocVnPayThanhToan
                    );
            model.addAttribute("thongBao", "Thanh toan thanh cong !");
            model.addAttribute("donHang", donHang);


        } else {
            model.addAttribute("thongBao", "Thanh toan không thanh cong !");


        }
        return "/user/pay";
    }

    public DonHang luuDonHang(
            PhuongThucThanhToan phuongThucThanhToan,
            KhachHang khachHang,
            String tenNguoiNhan,
            String sdt,
            String thanhPho,
            String quanHuyehn,
            String phuongXa,
            String diaChiCuThe,
            List<DonHangChiTiet> DonHangChiTietDaDuocVnPayThanhToan
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
                        .diaChiCuThe(diaChiCuThe)
                        .build();
        ThongTinGiaoHang thongTinGiaoHangMoiLuu = this.iThongTinGiaoHangRepo.save(thongTinGiaoHangForCreateDonHang);

        donHangVuaKhoiTao.setThongTinGiaoHang(thongTinGiaoHangMoiLuu);

        //CHI TIET DON HANG
        for (DonHangChiTiet donHangChiTiet : DonHangChiTietDaDuocVnPayThanhToan) {
            // thêm đơn hàng cho các don hang chi tiết và lưu
            donHangChiTiet.setDonHang(donHangVuaKhoiTao);
            DonHangChiTiet donHangChiTietMoiLuuVaoDB = this.donHangChiTietRepo.save(donHangChiTiet);
        }

        // tiến hành lưu lại đơn hàng
        DonHang donHangSauKhiLuu = this.iDonHangRepo.save(donHangVuaKhoiTao);
        return donHangSauKhiLuu;
    }

    @GetMapping("/pay")
    public ModelAndView getPay(BigDecimal total) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        BigDecimal amountBigDecimal = total.multiply(BigDecimal.valueOf(100));
        System.out.println(amountBigDecimal);
        amountBigDecimal = amountBigDecimal.setScale(0, RoundingMode.HALF_UP); // Làm tròn số thập phân
        long amount = amountBigDecimal.longValue();
        System.out.println(amount);
        String bankCode = "NCB";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "172.20.10.2";

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        return new ModelAndView("redirect:" + paymentUrl);
    }
}
