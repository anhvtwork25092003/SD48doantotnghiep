package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.service.IKhachHangService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.booksstore.entities.KhachHang;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quan-ly")
public class ThongtintaikhoanController {
    @Autowired
    IKhachHangService khachHangService;

    List<KhachHang> pageOfKhachHang = new ArrayList<>();
    @GetMapping("/thong-tin/hien-thi")
    public String hienThiTrangQuanLyKhachHang(Model model) {
        pageOfKhachHang=khachHangService.findAll();
        model.addAttribute("pageOfKhachHang", pageOfKhachHang);
        return "user/thongtintaikhoan";
    }
    @PostMapping("/thong-tin/cap-nhat")
        public String thongtinsua(
                @RequestParam("idKhachHang") String idKhachHang,
                @RequestParam("hoVaTen") String hoVaTen,
                @RequestParam("sdt") String sdt,
                @RequestParam("ngaySinh") Date ngaySinh,
                @RequestParam("gioiTinh") String gioiTinh,
                @RequestParam("email") String email,
                @RequestParam("trangThai") String trangThai,
                @RequestParam("matKhau") String matKhau,
                @RequestParam("loaiKhachHang") String loaiKhachHang,
                @RequestParam("ngayTaoTaiKhoan") Date ngayTaoTaiKhoan

    ){
        KhachHang khachupdate= KhachHang.builder()
                .idKhachHang(Integer.parseInt(idKhachHang))
                .trangThai(Integer.parseInt(trangThai))
                .ngayTaoTaiKhoan(ngayTaoTaiKhoan)
                .matKhau(matKhau)
                .loaiKhachHang(loaiKhachHang)
                .hoVaTen(hoVaTen)
                .sdt(sdt)
                .ngaySinh(ngaySinh)
                .gioiTinh(Integer.parseInt(gioiTinh))
                .email(email)
                .build();
        this.khachHangService.save(khachupdate);
        return "redirect:/quan-ly/thong-tin/hien-thi";
    }

}
