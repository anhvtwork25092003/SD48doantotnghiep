package com.example.booksstore.controller.user;


import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.ThongTin;
import com.example.booksstore.repository.IThongTinRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
@RequestMapping("/quan-ly")
public class ThongTinController {

    @Autowired
    IThongTinRepository repository;

    //    @Value("src/main/resources/static/image/logoicon/")
//    private String uploadanhThongTin;
    @GetMapping("/thong-tin")
    public String GetThongTin(HttpSession session, Model model) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        } else if (nhanVien.getChucVu().equalsIgnoreCase("Nhan vien")) {
            model.addAttribute("loggedInUser", nhanVien);
            return "redirect:/quan-ly/don-hang/cho-xac-nhan";
        } else {
            model.addAttribute("loggedInUser", nhanVien);

        }

        model.addAttribute("loggedInThongTin", repository.findAll());
        return "user/thongtin/thanh_thongtin";
    }


    @PostMapping("/thong-tin/cap-nhat/moi")
    public String thongtinthaydoimoi(
            @RequestParam("idThongTin") Integer id,
            @RequestParam("diaChi") String diaChi,
            @RequestParam("soDienThoai1") String soDienThoai1,
            @RequestParam("soDienThoai2") String soDienThoai2,
            @RequestParam("email") String email

    ) throws IOException {

        ThongTin thongTin = ThongTin.builder()
                .idThongTin(id)
                .diaChi(diaChi)
                .soDienThoai1(soDienThoai1)
                .soDienThoai2(soDienThoai2)
                .email(email)
                .build();
        this.repository.save(thongTin);
        return "redirect:/quan-ly/thong-tin";
    }


}