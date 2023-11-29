package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.service.IKhachHangService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.booksstore.entities.KhachHang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/quan-ly")
public class ThongtintaikhoanController {
    @Autowired
    IKhachHangService khachHangService;

    @Autowired
    IKhachHangRepository repository;

    @GetMapping("/thong-tin/hien-thi")
    public String hienThiThongTin(Model model, HttpSession session) {
        // Retrieve the logged-in user from the session
        KhachHang loggedInUser = (KhachHang) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // Pass the user information to the Thymeleaf template
            model.addAttribute("loggedInUser", loggedInUser);

            return "user/thongtintaikhoan";
        } else {
            // Redirect to the login page if the user is not logged in
            return "redirect:/login/khach-hang";
        }
    }

    @PostMapping("/thong-tin/cap-nhat")
    public String thongtinsua(
            @RequestParam("idKhachHang") Integer idKhachHang,
            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("ngaySinh") String ngaySinh,
            @RequestParam("gioiTinh") Integer gioiTinh,
            @RequestParam("email") String email
            ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date getNgaySinhs = dateFormat.parse(ngaySinh);
            KhachHang khachHang = khachHangService.detail(idKhachHang);
            khachHang = KhachHang.builder()
                    .idKhachHang(idKhachHang)
                    .trangThai(khachHang.getTrangThai())
                    .ngayTaoTaiKhoan(khachHang.getNgayTaoTaiKhoan())
                    .matKhau(khachHang.getMatKhau())
                    .loaiKhachHang(khachHang.getLoaiKhachHang())
                    .hoVaTen(hoVaTen)
                    .sdt(sdt)
                    .ngaySinh(getNgaySinhs)
                    .gioiTinh(gioiTinh)
                    .email(email)
                    .build();

            khachHangService.save(khachHang);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:/quan-ly/thong-tin/hien-thi/" + idKhachHang;
    }


    @GetMapping("/thong-tin/hien-thi/{idKhachHang}")
    public String hienThiThongTin(@PathVariable Integer idKhachHang, HttpSession session) {
        KhachHang khachHang = repository.getById(idKhachHang);
        session.setAttribute("loggedInUser", khachHang);
        return "user/thongtintaikhoan";
//        return "redirect:/quan-ly/thong-tin/hien-thi";
    }

}
