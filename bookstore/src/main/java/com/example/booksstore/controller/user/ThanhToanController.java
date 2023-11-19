package com.example.booksstore.controller.user;

import com.example.booksstore.entities.GioHang;
import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/thanh-toan")
@Controller
public class ThanhToanController {

    // chuyển hướng trang thanh toán
    @GetMapping("/xac-nhan-thanh-toan")
    public String xacNhanThanhToan(HttpSession session,
                                   List<GioHangChiTiet> gioHangChiTietListForPay,
                                   String idGioHang,
                                   Model model) {
        // xác minh đăng nhập

        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        // chưa đăng nhập: chuyển đến trang chwua đăng nhập
        if (khachHangDangNhap == null) {
            // lấy giỏ hàng và list giỏ hàng chi tiết từ sesion
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTietListForPay);
            return "/user/ThanhToanChuaDangNhap";
        } else {
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTietListForPay);
            return "/user/ThanhToanDaDangNhap";
        }

    }

    @PostMapping("/thanh-toan/xac-nhan-len-don")
    public String xacNhanLenDon(){

        return "/user/pay";
    }
}
