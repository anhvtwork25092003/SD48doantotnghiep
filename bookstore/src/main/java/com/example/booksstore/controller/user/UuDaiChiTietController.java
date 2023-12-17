package com.example.booksstore.controller.user;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.service.IKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UuDaiChiTietController {
    @Autowired
    IKhuyenMaiService iKhuyenMaiService;
    @GetMapping("/uu-dai/uu-dai-chi-tiet")
    public String chiTietKhuyenMai(Model model, @RequestParam("idKhuyenMai") Integer idKhuyenMai) {
        KhuyenMai khuyenMai = iKhuyenMaiService.chiTietKhuyenMai(idKhuyenMai);
        Pageable pageable4khuyenmai = PageRequest.of(0, 1);
        Page<KhuyenMai> fourKhuyenMaiDangHienThi = this.iKhuyenMaiService.get4KhuyenMaiDangHienThi(pageable4khuyenmai, 1);
        model.addAttribute("khuyenmaihienthianhBanner", fourKhuyenMaiDangHienThi);
        model.addAttribute("chitietkhuyenmai",khuyenMai);
        return "user/UuDaiChiTiet";
    }



}
