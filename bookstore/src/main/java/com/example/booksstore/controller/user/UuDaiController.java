package com.example.booksstore.controller.user;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.service.IKhuyenMaiService;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable50khuyenmai = PageRequest.of(0, 100);

        Page<KhuyenMai> pageable50khuyenmai1 = this.iKhuyenMaiService.get4KhuyenMaiDangHienThi(pageable50khuyenmai, 1);
        model.addAttribute("khuyenmaihienthibaner", pageable50khuyenmai1);

        model.addAttribute("khuyenmaidanghienthi", iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1));
        return "user/UuDai";
    }


}
