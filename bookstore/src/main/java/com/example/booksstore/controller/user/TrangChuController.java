package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.IDanhGiarepository;
import com.example.booksstore.service.IKhuyenMaiService;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    IDanhGiarepository iDanhGiarepository;
    @Autowired
    IKhuyenMaiService iKhuyenMaiService;

    @GetMapping("/trang-chu")
    public String HienThiTrangChu(Model model) {
        List<Sach> sachList = iSachService.getall();
        List<Sach> saches = sachList.subList(0, 10);
        model.addAttribute("sachmoi", saches);
        if (iKhuyenMaiService.getAllKhuyenMaiDangApDung(1) != null) {
            model.addAttribute("motkhuyenmaidangapdung", iKhuyenMaiService.getAllKhuyenMaiDangApDung(1).get(0));
        }
        List<KhuyenMai> khuyenMaiList = iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1);
        List<KhuyenMai> khuyenMaiLists = khuyenMaiList.subList(0, 4);
        model.addAttribute("khuyenmaihienthisale", khuyenMaiLists);
        model.addAttribute("khuyenmaihienthisach", iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1));
        return "user/TrangChu";
    }

    @GetMapping("/trang-chu/detail")
    public String detail(@RequestParam("idSach") Integer idSach, Model model) {
        Sach sach = iSachService.dateil(idSach);
        model.addAttribute("listSach", sach);
        int trangthai=1;
        List<DanhGia> danhGia = this.iDanhGiarepository.findAllBySach_IdSachAndTrangThai(idSach,trangthai);
        for(DanhGia danhGia1:danhGia){
            System.out.println(danhGia1.getIdDanhGia());
        }
        model.addAttribute("listdg",danhGia);
        return "user/ChiTietSanPham";
    }

}
