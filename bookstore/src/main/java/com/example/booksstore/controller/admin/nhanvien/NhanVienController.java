package com.example.booksstore.controller.admin.nhanvien;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.repository.NhanVienRepository;
import com.example.booksstore.service.INhanVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

@Controller
public class NhanVienController {
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    INhanVienService service;
    @Value("${upload.anhnhanvien}")
    private String uploadanhnhanvien;
    @GetMapping("/tong-quan-nhan-vien")
    public String nhanVienTongQuan(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien != null) {
            model.addAttribute("loggedInUser", nhanVien);
//            return "/admin/nhanvien/layoutchungnhanvien/menuNhanVien";
//            return "/admin/quanly/DonHangCho";
            return "redirect:/quan-ly/don-hang/cho-xac-nhan";
        }
        return "redirect:/login";
    }
    @GetMapping("/thong-tin/hien-thi")
    public String hienThiThongTin(HttpSession session,Model model) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        session.setAttribute("loggedInUser", nhanVien);
        model.addAttribute("loggedInUser", nhanVien);
        return "admin/nhanvien/thongtintaikhoanNhanVien";
//        return "redirect:/quan-ly/thong-tin/hien-thi";
    }
    @PostMapping("/nhan-vien/thay-doi")
    public String nhanvienthaydoi(
            @RequestParam("idNhanVien") String idNhanVien,
            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("ngaySinh") Date ngaySinh,
            @RequestParam("email") String email

    ) throws IOException {
        NhanVien nhanVien = this.service.getOne(Integer.parseInt(idNhanVien));
        NhanVien nhanvienUpDate = NhanVien.builder()
                .idNhanVien(Integer.parseInt(idNhanVien))
                .maNhanVien(nhanVien.getMaNhanVien())
                .hoVaTen(hoVaTen)
                .sdt(sdt)
                .ngaySinh(ngaySinh)
                .trangThai(nhanVien.getTrangThai())
                .matKhau(nhanVien.getMatKhau())
                .email(email)
                .chucVu(nhanVien.getChucVu())
                .linkAnhNhanVien(nhanVien.getLinkAnhNhanVien())
                .build();
        this.service.save(nhanvienUpDate);
        return "redirect:/thong-tin/hien-thi/"+idNhanVien;
    }
}
