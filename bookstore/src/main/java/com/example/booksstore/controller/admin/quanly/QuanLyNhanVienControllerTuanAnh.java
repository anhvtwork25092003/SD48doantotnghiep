package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Set;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyNhanVienControllerTuanAnh {
    @Autowired
    INhanVienService service;
    @Value("${upload.anhnhanvien}")
    private String uploadanhnhanvien;

    @GetMapping("/nhan-vien/hien-thi")
    public String hienThiManHinhQuanlyNhanVien(Model model, @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(required = false) String memberNameSearch,
                                               @RequestParam(required = false) String memberCodeSearch,
                                               @RequestParam(required = false) String memberStatusSearch
                                              ) {
        Page<NhanVien> pageOfNhanVien;
        int trangThai = 0;
        int pageSize = 2; // Đặt kích thước trang mặc định
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0
//  moi khoi tao trang
        if (memberNameSearch != null || memberCodeSearch != null || memberStatusSearch != null) {

            // xuu ly trang thai
            if (memberStatusSearch.equals("99")) {
                trangThai = -1;
            } else if (memberStatusSearch.equals("1")) {
                trangThai = 1;
            } else if (memberStatusSearch.equals("0")) {
                trangThai = 0;
            }
            pageOfNhanVien = service.searchNhanVien(memberNameSearch,memberCodeSearch,trangThai,pageable);
        } else {
            pageOfNhanVien = service.pageOfNhanVien(pageable);
        }

        model.addAttribute("pageOfNhanVien", pageOfNhanVien);
        return "/user/nhanvien/thanh_nhanvien";
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
            String duongDanCotDinh = "/image/anhnhanvien/";
            String duongDanLuuAnh = duongDanCotDinh + linkAnhNhanVien.getOriginalFilename();
            System.out.println(duongDanCotDinh + linkAnhNhanVien.getOriginalFilename());
            if (linkAnhNhanVien.isEmpty()) {
                duongDanLuuAnh = "";
            } else {
                byte[] bytes = linkAnhNhanVien.getBytes();
                Path path = Paths.get(uploadanhnhanvien + linkAnhNhanVien.getOriginalFilename());
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
        return "redirect:/quan-ly/nhan-vien/hien-thi";
    }

    @PostMapping("/nhan-vien/cap-nhat")
    public String nhanviensua(
            @RequestParam("checkthayDoiImage") String trangThaiThayDoiAnh1,
            @RequestParam("idNhanVien") String idNhanVien,
            @RequestParam("maNhanVien") String maNhanVien,
            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("ngaySinh") Date ngaySinh,
            @RequestParam("trangThai") String trangThai,
            @RequestParam("matKhau") String matKhau,
            @RequestParam("email") String email,
            @RequestParam("chucVu") String chucVu,
            @RequestParam("editlinkAnhNhanVien") MultipartFile linkAnhNhanVien

    ) throws IOException {
        System.out.println(trangThaiThayDoiAnh1);
        NhanVien nhanVien = this.service.getOne(Integer.parseInt(idNhanVien));
        String duongDanCotDinh = "/image/anhnhanvien/";
        String duongDanLuuAnhNhanVien = "";
        if (trangThaiThayDoiAnh1.equalsIgnoreCase("DaThayDoi")) {
            if (linkAnhNhanVien.isEmpty()) {
                duongDanLuuAnhNhanVien = "";
            } else {
                byte[] bytes = linkAnhNhanVien.getBytes();
                Path path = Paths.get(uploadanhnhanvien + linkAnhNhanVien.getOriginalFilename());
                Files.write(path, bytes);
                duongDanLuuAnhNhanVien = duongDanCotDinh + linkAnhNhanVien.getOriginalFilename();
            }
        } else {
            duongDanLuuAnhNhanVien = nhanVien.getLinkAnhNhanVien();
        }
        NhanVien nhanvienUpDate = NhanVien.builder()
                .idNhanVien(Integer.parseInt(idNhanVien))
                .maNhanVien(maNhanVien)
                .hoVaTen(hoVaTen)
                .sdt(sdt)
                .ngaySinh(ngaySinh)
                .trangThai(Integer.parseInt(trangThai))
                .matKhau(matKhau)
                .email(email)
                .chucVu(chucVu)
                .linkAnhNhanVien(duongDanLuuAnhNhanVien)
                .build();
        this.service.save(nhanvienUpDate);
        return "redirect:/quan-ly/nhan-vien/hien-thi";
    }

}
