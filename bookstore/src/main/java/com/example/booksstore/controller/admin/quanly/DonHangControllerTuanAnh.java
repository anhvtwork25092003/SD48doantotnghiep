package com.example.booksstore.controller.admin.quanly;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly")
public class DonHangControllerTuanAnh {
    @GetMapping("/don-hang/cho-xac-nhan")
    public String hienThiTrangTongQuanQuanLy() {
        return "/admin/quanly/DonHangCho";
    }

    @GetMapping("/don-hang/cho-van-chuyen")
    public String quanLyChoVanChuyen() {
        return "/admin/quanly/DonHangDaDuyet";
    }

    @GetMapping("/don-hang/dang-giao-hang")
    public String quanLyDangGiaoHang() {
        return "/admin/quanly/DonHangDaHoanThanh";
    }

    @GetMapping("/don-hang/giao-thanh-cong")
    public String quanLyGiaoThanhCong() {
        return "/admin/quanly/DonHangDaHuy";
    }

//
//    @GetMapping("/don-hang/da-huy")
//    public String quanLyDaHuy() {
//        return "/admin/quanly/DonHangDaHuy";
//    }


//    @GetMapping("/don-hang/doi-tra")
//    public String quanLyDoiTra() {
//        return "/admin/quanly/DonHangCho";
//    }
}
