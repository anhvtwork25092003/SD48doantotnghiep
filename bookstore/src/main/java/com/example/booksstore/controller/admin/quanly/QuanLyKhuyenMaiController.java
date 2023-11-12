package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.IKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyKhuyenMaiController {

    @Autowired
    IKhuyenMaiService iKhuyenMaiService;

    @Autowired
    ISachRepository repository;

    @GetMapping("/khuyen-mai/hien-thi")
    public String hienThiTrangKhuyenMai(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5; // Đặt kích thước trang mặc định
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0
        Page<KhuyenMai> khuyenMaiPages;
        khuyenMaiPages = iKhuyenMaiService.getAllKhuyenMaiTheoTrangThai(pageable, 1);

        model.addAttribute("sachs", repository.findAll());
        model.addAttribute("khuyenMaiPages", khuyenMaiPages);
        return "/admin/quanly/KhuyenMai2";
    }

    @Transactional
    @PostMapping("/khuyen-mai/them-moi")
    public String themMoiKhuyenMai(
            RedirectAttributes redirectAttributes,
            @RequestParam("tenKhuyenMai") String tenKhuyenMai,
            @RequestParam("ngayBatDau") String ngayBatDau,
            @RequestParam("soPhanTramGiamGia") String soPhanTramGiamGia,
            @RequestParam("ngayKetThuc") String ngayKetThuc,
            @RequestParam("trangThai") String trangThai,
            @RequestParam("sachKM") Set<Sach> sachKM,
            @RequestParam("trangThaiHienThi") String trangThaiHienThi,
            Model model
    ) {
        // xử lý Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dateNgayBatDau = null;
        Date dateNgayKetThuc = null;
        try {
            // Thêm cứng giây thành "00"
            ngayBatDau = ngayBatDau + ":00";
            ngayKetThuc = ngayKetThuc + ":00";
            dateNgayBatDau = dateFormat.parse(ngayBatDau);
            dateNgayKetThuc = dateFormat.parse(ngayKetThuc);
            List<String> result = iKhuyenMaiService.layThongTinSachTrongKhuyenMai(sachKM, dateNgayBatDau, dateNgayKetThuc);
            if (result.isEmpty()) {
                // active for add new books
                KhuyenMai khuyenMai = KhuyenMai.builder()
                        .tenKhuyenMai(tenKhuyenMai)
                        .soPhanTramGiamGia(Integer.parseInt(soPhanTramGiamGia))
                        .ngayBatDau(dateNgayBatDau)
                        .ngayKetThuc(dateNgayKetThuc)
                        .trangThai(Integer.parseInt(trangThai))
                        .trangThaiHienThi(Integer.parseInt(trangThaiHienThi))
                        .sachs(sachKM)
                        .build();
                redirectAttributes.addFlashAttribute("blankError", iKhuyenMaiService.SaveOrUpdateKhuyenMai(khuyenMai));
            } else {
                // co sach bị trùng khuyến mãi, không thêm, quay lại báo lỗi ra
                model.addAttribute("data", result);
                System.out.println(result);
            }
            if (tenKhuyenMai.trim().length() == 0 || soPhanTramGiamGia.trim().length() == 0) {
                redirectAttributes.addFlashAttribute("blankError", "Không được để trống thông tin!");
                return "redirect:/quan-ly/khuyen-mai/hien-thi";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/quan-ly/khuyen-mai/hien-thi";
    }

    @Transactional
    @PostMapping("/khuyen-mai/cap-nhat")
    public String suaKhuyenMai(
            @RequestParam("idKhuyenMai") String idKhuyenMai,
            @RequestParam("tenKhuyenMai") String tenKhuyenMai,
            @RequestParam("soPhanTramGiamGia") String soPhanTramGiamGia,
            @RequestParam("ngayBatDau") String ngayBatDau,
            @RequestParam("ngayKetThuc") String ngayKetThuc,
            @RequestParam("trangThai") String trangThai,
            @RequestParam("sachKM2") Set<Sach> sachKM2,
            @RequestParam("trangThaiHienThi") String trangThaiHienThi,
            Model model
    ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date dateNgayBatDau = null;
        Date dateNgayKetThuc = null;

        try {
            // Thêm cứng giây thành "00"
            ngayBatDau = ngayBatDau + ":00";
            ngayKetThuc = ngayKetThuc + ":00";
            dateNgayBatDau = dateFormat.parse(ngayBatDau);
            dateNgayKetThuc = dateFormat.parse(ngayKetThuc);
            List<String> result = iKhuyenMaiService.layThongTinSachTrongKhuyenMaiChoUpdate(sachKM2, dateNgayBatDau, dateNgayKetThuc, Integer.parseInt(idKhuyenMai));
            if (result.isEmpty()) {
                KhuyenMai KMupdate = KhuyenMai.builder()
                        .idKhuyenMai(Integer.parseInt(idKhuyenMai))
                        .soPhanTramGiamGia(Integer.parseInt(soPhanTramGiamGia))
                        .tenKhuyenMai(tenKhuyenMai)
                        .ngayBatDau(dateNgayBatDau)
                        .ngayKetThuc(dateNgayKetThuc)
                        .trangThai(Integer.parseInt(trangThai))
                        .trangThaiHienThi(Integer.parseInt(trangThaiHienThi))
                        .sachs(sachKM2)
                        .build();
                iKhuyenMaiService.SaveOrUpdateKhuyenMai(KMupdate);
                return "redirect:/quan-ly/khuyen-mai/hien-thi";
            } else {
                // co sach bị trùng khuyến mãi, không thêm, quay lại báo lỗi ra
                model.addAttribute("data", result);
                System.out.println(result);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần thiết
        }
        return "redirect:/quan-ly/khuyen-mai/hien-thi";
    }


}
