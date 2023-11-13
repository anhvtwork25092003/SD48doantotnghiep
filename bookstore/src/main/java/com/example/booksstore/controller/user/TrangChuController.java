package com.example.booksstore.controller.user;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TacGia;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TrangChuController {
    @Autowired
    ISachService iSachService;
    @Autowired
    ITheLoaiServiec iTheLoaiServiec;
    @Autowired
    TacGiaService tacGiaService;

    @GetMapping("/trang-chu")
    public String HienThiTrangChu(Model model) {
        List<Sach> sachList = iSachService.sachmoi();
        List<Sach> saches = sachList.subList(0, 5);
        model.addAttribute("sachmoi", saches);
        return "user/TrangChu";
    }

    @GetMapping("/trang-chu/detail")
    public String detail(@RequestParam("idSach") Integer idSach, Model model) {
        Sach sach = iSachService.dateil(idSach);
        model.addAttribute("listSach", sach);
        model.addAttribute("author", iTheLoaiServiec.findAllTheLoai());
        return "user/ctsp/index";
    }

}
