package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.service.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyNhanVienControllerTuanAnh {
    @Autowired
    INhanVienService service;
    @Value("${upload.directory}")
    private String uploadDirectory;

    @GetMapping("/nhan-vien/hien-thi")
    public String hienThiManHinhQuanlyNhanVien(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<NhanVien> pageOfNhanVien = service.pageOfNhanVien(pageable);
        model.addAttribute("pageOfNhanVien", pageOfNhanVien);
        return "/user/nhanvien/theanh_nhanvien";
    }

    @Transactional
    @PostMapping("/nhan-vien/them-moi")
    public String themNhanVien(
            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("ngaySinh") Date ngaySinh,
            @RequestParam("trangThai") String trangThai,
            @RequestParam("matKhau") String matKhau,
            @RequestParam("email") String email,
            @RequestParam("chucVu") String chucVu,
            @RequestParam("linkAnhNhanVien") MultipartFile linkAnhNhanVien) {
        try {
            String duongDanCotDinh = "/image/anhsanpham/";
            String duongDanLuuAnh = duongDanCotDinh + linkAnhNhanVien.getOriginalFilename();
            System.out.println(duongDanCotDinh + linkAnhNhanVien.getOriginalFilename());
            if (linkAnhNhanVien.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh = "";
            } else {
                byte[] bytes = linkAnhNhanVien.getBytes();
                Path path = Paths.get(uploadDirectory + linkAnhNhanVien.getOriginalFilename());
                Files.write(path, bytes);

            }
            NhanVien nhanvien = NhanVien.builder()
                    .hoVaTen(hoVaTen)
                    .sdt(sdt)
                    .ngaySinh(ngaySinh)
                    .trangThai(Integer.parseInt(trangThai))
                    .matKhau(matKhau)
                    .email(email)
                    .chucVu(chucVu)
                    .linkAnhNhanVien(duongDanLuuAnh)
                    .build();

            this.service.save(nhanvien);

        } catch (Exception e) {
            e.printStackTrace();
        }

//sau khi them moi thanh cong, chuyen ve trang chu
        return "redirect:/quan-ly/nhan-vien/hien-thi";
    }
}
