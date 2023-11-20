package com.example.booksstore.controller.user;

import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.PhuongThucThanhToanRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/thanh-toan")
@Controller
public class ThanhToanController {

    @Autowired
    PhuongThucThanhToanRepo phuongThucThanhToanRepo;

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
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTietListDaChon);
            return "/user/ThanhToanDaDangNhap";
        }

    }

    @PostMapping("/xac-nhan-len-don")
    public String xacNhanLenDon(HttpSession session,
                                @RequestParam(value = "danhSachChonMua", required = false) List<GioHangChiTiet> gioHangChiTietListForPay,
                                @RequestParam("phuongThucThanhToanMaKhachHangChon") String phuongThucThanhToan,
                                @RequestParam("tenNguoiNhan") String tenNguoiNhan,
                                @RequestParam("soDienThoaiNhanHang") String soDienThoaiNhanHang,
                                @RequestParam("thanhPho") String thanhPho,
                                @RequestParam("quanHuyen") String quanHuyen,
                                @RequestParam("phuongXa") String phuongXa,
                                @RequestParam("diaChiCuThe") String diaChiCuThe


    ) {
// xác minh đăng nhậpS
        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        if (khachHangDangNhap == null) {
            // chưa đăng nhạp
            // giảm trừ list trong sesion  va tạo đơn hàng với nhân viên trống, trạng thá
            // phân biệt phương thức thanh toán
            // nếu chọn thanh toán trực tuyến thì sẽ chuyển đến vn pay
            // nếu chọn thanh toán bằng tiền mặt => đặt hàng thành công, chuyển thông tin đơn lên admin


        } else {
            // đã đăng nhập rồi nè
        }
        return "/user/pay";
    }
}
