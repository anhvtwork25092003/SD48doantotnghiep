package com.example.booksstore.controller.user;

import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.IDanhGiarepository;
import com.example.booksstore.service.*;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    IKiemTraDanhGiaService iKiemTraDanhGiaService;

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
        Pageable pageable50khuyenmai = PageRequest.of(0, 100);
        Page<KhuyenMai> fourKhuyenMaiDangHienThi = this.iKhuyenMaiService.get4KhuyenMaiDangHienThi(pageable4khuyenmai, 1);
        Page<KhuyenMai> pageable50khuyenmai1 = this.iKhuyenMaiService.get4KhuyenMaiDangHienThi(pageable50khuyenmai, 1);
        model.addAttribute("khuyenmaihienthisale", fourKhuyenMaiDangHienThi);
        model.addAttribute("khuyenmaihienthibaner", pageable50khuyenmai1);
        model.addAttribute("khuyenmaihienthisach", iKhuyenMaiService.getAllKhuyenMaiDangHienThi(1));
        return "user/TrangChu";
    }

    @GetMapping("/trang-chu/detail")
    public String detail(@RequestParam("idSach") Integer idSach, Model model, HttpSession session) {
        Sach sach = iSachService.dateil(idSach);
        model.addAttribute("listSach", sach);

        // Check if the user is logged in
        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");

        if (khachHangDangNhap != null) {
            // Get the user's ability to review
            int idKhachHang = khachHangDangNhap.getIdKhachHang();
            boolean trangThaiDuocPhepDanhGia = iKiemTraDanhGiaService.checkKhaNangDanhGia(idKhachHang, idSach);
            model.addAttribute("khanangdanhgia", trangThaiDuocPhepDanhGia);
        }

        int trangthai = 1;
        List<DanhGia> danhGia = this.iDanhGiarepository.findAllBySach_IdSachAndTrangThai(idSach, trangthai);
        model.addAttribute("listdg", danhGia);
        return "user/ChiTietSanPham";
    }


}
