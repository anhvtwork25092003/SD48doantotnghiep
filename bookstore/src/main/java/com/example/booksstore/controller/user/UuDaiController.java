package com.example.booksstore.controller.user;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.service.IKhuyenMaiService;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UuDaiController {
    @Autowired
    ISachService iSachService;
    @Autowired
    IKhuyenMaiService iKhuyenMaiService;

    @Autowired
    ITheLoaiServiec iTheLoaiServiec;
    @Autowired
    TacGiaService tacGiaService;

    @GetMapping("/uu-dai")
    public String HienThiTrangUuDaiLon(Model model){
        model.addAttribute("khuyenmaidanghienthi", iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1));
        return "user/UuDai";
    }
    @GetMapping("/uu-dai/uu-dai-chi-tiet")
    public String chiTietKhuyenMai(Model model, @RequestParam("idKhuyenMai") Integer idKhuyenMai) {
        KhuyenMai khuyenMai = iKhuyenMaiService.chiTietKhuyenMai(idKhuyenMai);
        model.addAttribute("chitietkhuyenmai",khuyenMai);
        return "user/UuDaiChiTiet";
    }

}
