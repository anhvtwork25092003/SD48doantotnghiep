package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IDiaChiRepository;
import com.example.booksstore.repository.IKhachHangRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/thanh-toan")
@Controller
// các controller trong này phục vụ cho thanh toán, thế anh khỏi quan tâm
public class DiaChiController {

    @Autowired
    IDiaChiRepository iDiaChiRepository;

    @Autowired
    IKhachHangRepository iKhachHangRepository;

    @PostMapping("/them-dia-chi")
    public String themDiaChi(HttpSession session,
                             @RequestParam("tinhThanhPhoThemMoi") String idTinhThanhPho,
                             @RequestParam("huyenQuanThemMoi") String huyenQuanThemMoi,
                             @RequestParam("xaPhuongThemMoi") String xaPhuongThemMoi,
                             @RequestParam("diaChiCuTheThemMoi") String diaChiCuTheThemMoi
    ) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        DiaChi diaChi = DiaChi.builder()
                .tinhThanhPho(idTinhThanhPho)
                .huyenQuan(huyenQuanThemMoi)
                .xaPhuong(xaPhuongThemMoi)
                .diaChiCuThe(diaChiCuTheThemMoi)
                .khachHangDiaChi(khachHang)
                .build();
        DiaChi diaChiMoiThem = this.iDiaChiRepository.save(diaChi);
        KhachHang khachHangCapNhat = this.iKhachHangRepository.findById(khachHang.getIdKhachHang()).get();
        session.setAttribute("loggedInUser", khachHangCapNhat);
        return "redirect:/thanh-toan/xac-nhan-thanh-toan";
    }
}
