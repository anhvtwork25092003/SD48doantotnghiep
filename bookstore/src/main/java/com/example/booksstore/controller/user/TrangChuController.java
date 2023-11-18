package com.example.booksstore.controller.user;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TacGia;
import com.example.booksstore.repository.IKhuyenMaiReporitory;
import com.example.booksstore.repository.ISachRepository;
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

    @Autowired
    IKhuyenMaiService iKhuyenMaiService;

    @GetMapping("/trang-chu")
    public String HienThiTrangChu(Model model) {
//        List<Sach> sachList = iSachService.getall();
//        List<Sach> saches = sachList.subList(0, 5);
//        model.addAttribute("sachmoi", saches);
        model.addAttribute("motkhuyenmaidangapdung",iKhuyenMaiService.getAllKhuyenMaiDangApDung(1).get(0));
        List<KhuyenMai> khuyenMaiList= iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1);
        List<KhuyenMai> khuyenMaiLists = khuyenMaiList.subList(0, 4);
        model.addAttribute("khuyenmaihienthisale", khuyenMaiLists);
        model.addAttribute("khuyenmaihienthisach", iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1));
        return "user/TrangChu";
    }

    @GetMapping("/trang-chu/detail")
    public String detail(@RequestParam("idSach") Integer idSach, Model model) {
        Sach sach = iSachService.dateil(idSach);
        model.addAttribute("listSach", sach);
        return "user/ChiTietSanPham";
    }

}
