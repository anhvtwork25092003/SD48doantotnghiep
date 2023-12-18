package com.example.booksstore.controller.user;


import com.example.booksstore.entities.ThongTin;
import com.example.booksstore.repository.IThongTinRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/quan-ly")
public class ThongTinController {

    @Autowired
    IThongTinRepository repository;

    @GetMapping("/thong-tin")
    public String GetThongTin(HttpSession session, Model model) {
        ThongTin loggedInThongTin = (ThongTin) session.getAttribute("loggedInThongTin");
        model.addAttribute("loggedInThongTin", loggedInThongTin);
        return "user/thongtin/thanh_thongtin";
    }

    @PostMapping("/thong-tin/cap-nhat/moi")
    public String thongtinthaydoimoi(
            @RequestParam("idThongTin") Integer id,
            @RequestParam("diaChi") String diaChi,
            @RequestParam("soDienThoai1") String soDienThoai1,
            @RequestParam("soDienThoai2") String soDienThoai2,
            @RequestParam("email") String email,
            @RequestParam("linkBannerTrangChu") MultipartFile linkAnhTrangChu,
            @RequestParam(" linkLogo") MultipartFile linklogo
    ) {

        return "redirect:user/thongtin/thanh_thongtin" + id;
    }
}