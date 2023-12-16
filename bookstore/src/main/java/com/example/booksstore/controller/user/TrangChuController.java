package com.example.booksstore.controller.user;

import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.IDanhGiarepository;
import com.example.booksstore.service.IKhuyenMaiService;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
import com.example.booksstore.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

    @Autowired
    ThongKeService thongKeService;

    @GetMapping("/trang-chu")
    public String HienThiTrangChu(Model model) {
        Pageable pageableSachMoi = PageRequest.of(0, 10);
        Page<Sach> saches = iSachService.sachmoi(pageableSachMoi);
        model.addAttribute("sachmoi", saches);
        // top sản phẩm bán chạy gồm 5 sác
        List<TopSanPhamDTO> topSanPhamDTOS = this.thongKeService.getTopSanPhamBanChayNhat();
        if (topSanPhamDTOS.size() > 0) {
            List<Sach> listSachBanChay = new ArrayList<>();
            for (TopSanPhamDTO topSanPhamDTO : topSanPhamDTOS) {
                Sach s = this.iSachService.getOne(topSanPhamDTO.getIdSach());
                if (s.getTrangThai() == 1) {
                    listSachBanChay.add(s);
                }
            }
            model.addAttribute("listSachBanChay", listSachBanChay);
        }

        if (iKhuyenMaiService.getAllKhuyenMaiDangApDung(1).size() > 0) {
            model.addAttribute("motkhuyenmaidangapdung", iKhuyenMaiService.getAllKhuyenMaiDangApDung(1).get(0));
        }
        Pageable pageable4khuyenmai = PageRequest.of(0, 4);
        Page<KhuyenMai> fourKhuyenMaiDangHienThi = this.iKhuyenMaiService.get4KhuyenMaiDangHienThi(pageable4khuyenmai, 1);
        model.addAttribute("khuyenmaihienthisale", fourKhuyenMaiDangHienThi);
        model.addAttribute("khuyenmaihienthisach", iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1));
        return "user/TrangChu";
    }

    @GetMapping("/trang-chu/detail")
    public String detail(@RequestParam("idSach") Integer idSach, Model model) {
        Sach sach = iSachService.dateil(idSach);
        model.addAttribute("listSach", sach);
        int trangthai = 1;
        List<DanhGia> danhGia = this.iDanhGiarepository.findAllBySach_IdSachAndTrangThai(idSach, trangthai);
        for (DanhGia danhGia1 : danhGia) {
            System.out.println(danhGia1.getIdDanhGia());
        }
        model.addAttribute("listdg", danhGia);
        return "user/ChiTietSanPham";
    }

}
