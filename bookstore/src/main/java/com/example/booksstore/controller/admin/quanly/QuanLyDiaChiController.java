package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IDiaChiRepository;
import com.example.booksstore.service.IDiaChiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/menu-nguoi-dung")
public class QuanLyDiaChiController {

    @Autowired
    IDiaChiService iDiaChiService;

    @Autowired
    IDiaChiRepository iDiaChiRepository;

    @GetMapping("/dia-chi/hien-thi")
    public String hienThiTrangDiaChi(Model model, @RequestParam(defaultValue = "1") int page,
                                     HttpSession session) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        List<DiaChi> danhSachDiaChi = iDiaChiRepository.findAllByKhachHangDiaChi(khachHang);
        model.addAttribute("pageOfDiaChi", danhSachDiaChi);
        return "user/diachinhanhang";
    }

    @PostMapping("/dia-chi/them-dia-chi")
    public String themDiaChi(HttpSession session,
                             @RequestParam("tinhThanhPhoThemMoi") String idTinhThanhPho,
                             @RequestParam("huyenQuanThemMoi") String huyenQuanThemMoi,
                             @RequestParam("xaPhuongThemMoi") String xaPhuongThemMoi,
                             @RequestParam("diaChiCuTheThemMoi") String diaChiCuTheThemMoi,
                             @RequestParam("TenNguoiNhanThemMoi") String TenNguoiNhanThemMoi,
                             @RequestParam("sdtnhanHangThemMoi") String sdtnhanHangThemMoi
    ) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        DiaChi diaChi = DiaChi.builder()
                .tinhThanhPho(idTinhThanhPho)
                .huyenQuan(huyenQuanThemMoi)
                .xaPhuong(xaPhuongThemMoi)
                .diaChiCuThe(diaChiCuTheThemMoi)
                .khachHangDiaChi(khachHang)
                .tenNguoiNhan(TenNguoiNhanThemMoi)
                .sdtNguoiNhanHang(sdtnhanHangThemMoi)
                .build();
        DiaChi diaChiMoiThem = this.iDiaChiRepository.save(diaChi);
        return "redirect:/menu-nguoi-dung/dia-chi/hien-thi";
    }

    @PostMapping("/dia-chi/sua-dia-chi")
    public String suaDiaChi(HttpSession session,
                            @RequestParam("idDiaChi") String idDiaChi,
                            @RequestParam("tinhThanhPhoThemMoi") String idTinhThanhPho,
                            @RequestParam("huyenQuanThemMoi") String huyenQuanThemMoi,
                            @RequestParam("xaPhuongThemMoi") String xaPhuongThemMoi,
                            @RequestParam("diaChiCuTheThemMoi") String diaChiCuTheThemMoi,
                            @RequestParam("TenNguoiNhanThemMoi") String TenNguoiNhanThemMoi,
                            @RequestParam("sdtnhanHangThemMoi") String sdtnhanHangThemMoi
    ) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        DiaChi diaChi = DiaChi.builder()
                .idDiaChi(Integer.parseInt(idDiaChi))
                .tinhThanhPho(idTinhThanhPho)
                .huyenQuan(huyenQuanThemMoi)
                .xaPhuong(xaPhuongThemMoi)
                .diaChiCuThe(diaChiCuTheThemMoi)
                .khachHangDiaChi(khachHang)
                .tenNguoiNhan(TenNguoiNhanThemMoi)
                .sdtNguoiNhanHang(sdtnhanHangThemMoi)
                .build();
        DiaChi diaChiMoiThem = this.iDiaChiRepository.save(diaChi);
        return "redirect:/menu-nguoi-dung/dia-chi/hien-thi";
    }

    @GetMapping ("/xoa-dia-chi/{idDiaChiDelete}")
    public String xoaDiaChi(HttpSession session,
                            @PathVariable int idDiaChiDelete
    ) {
        DiaChi diaChiforDelete = this.iDiaChiRepository.findById(idDiaChiDelete).get();
        this.iDiaChiRepository.delete(diaChiforDelete);
        return "redirect:/menu-nguoi-dung/dia-chi/hien-thi";
    }
}
