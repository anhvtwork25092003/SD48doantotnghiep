package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.service.IDiaChiService;
import com.example.booksstore.service.IKhachHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyKhachHangController {

    @Autowired
    IKhachHangService iKhachHangService;

    @Autowired
    IDiaChiService iDiaChiService;

    @GetMapping("/khach-hang/hien-thi")
    public String hienThiTrangQuanLyKhachHang(Model model, @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(required = false) String maKhachHangTimKiem,
                                              @RequestParam(required = false) String sdtTimKiem,
                                              @RequestParam(required = false) String trangThaiTimKiem,
                                              HttpSession session
    ) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("loggedInUser", nhanVien);

        }
        Page<KhachHang> pageOfKhachHang;
        int pageSize = 5; //Đặt kích thước trang
        int trangThai = 0;
        Pageable pageable = PageRequest.of(page - 1, pageSize); // số trang bắt đầu từ 0
        boolean daTimKiem = (trangThaiTimKiem != null);
        if (maKhachHangTimKiem != null || sdtTimKiem != null || trangThaiTimKiem != null) {
            // xử lý trạng thái
            if (trangThaiTimKiem.equals("99")) {
                trangThai = -1;
            } else if (trangThaiTimKiem.equals("1")) {
                trangThai = 1;
            } else if (trangThaiTimKiem.equals("0")) {
                trangThai = 0;
            }
            model.addAttribute("trangThai", trangThaiTimKiem);

            pageOfKhachHang = iKhachHangService.searchKhachHang(maKhachHangTimKiem, sdtTimKiem, trangThai, pageable);
        } else {
            pageOfKhachHang = iKhachHangService.pageOfKhachHang(pageable);
        }
        model.addAttribute("daTimKiem", daTimKiem);
        model.addAttribute("pageOfKhachHang", pageOfKhachHang);
        model.addAttribute("listDiaChi", iDiaChiService.finAll(pageable));
        return "admin/quanly/KhachHang";
    }

    @PostMapping("/khach-hang/cap-nhat")
    public String thongtinsua(
            @RequestParam("idKhachHang") Integer idKhachHang,
            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("ngaySinh") String ngaySinh,
            @RequestParam("gioiTinh") Integer gioiTinh,
            @RequestParam("email") String email,
            @RequestParam("trangThai") String trangThai
    ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date getNgaySinhs = dateFormat.parse(ngaySinh);
            KhachHang khachHang = iKhachHangService.detail(idKhachHang);
            khachHang = KhachHang.builder()
                    .idKhachHang(idKhachHang)
                    .trangThai(khachHang.getTrangThai())
                    .ngayTaoTaiKhoan(khachHang.getNgayTaoTaiKhoan())
                    .matKhau(khachHang.getMatKhau())
                    .loaiKhachHang(khachHang.getLoaiKhachHang())
                    .hoVaTen(hoVaTen)
                    .sdt(sdt)
                    .ngaySinh(getNgaySinhs)
                    .gioiTinh(gioiTinh)
                    .email(email)
                    .trangThai(Integer.parseInt(trangThai))
                    .build();

            iKhachHangService.save(khachHang);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:/quan-ly/khach-hang/hien-thi";
    }


//    @PostMapping("/khach-hang/cap-nhat")
//    public String khachHangsua(
//            @RequestParam("idKhachHang") String idKhachHang,
//            @RequestParam("hoVaTen") String hoVaTen,
//            @RequestParam("sdt") String sdt,
//            @RequestParam("ngaySinh") Date ngaySinh,
//            @RequestParam("gioiTinh") String gioiTinh,
//            @RequestParam("email") String email,
//            @RequestParam("trangThai") String trangThai,
//            @RequestParam("matKhau") String matKhau,
//            @RequestParam("loaiKhachHang") String loaiKhachHang,
//            @RequestParam("ngayTaoTaiKhoan") Date ngayTaoTaiKhoan
//
//    ) {
//        KhachHang khachupdate = KhachHang.builder()
//                .idKhachHang(Integer.parseInt(idKhachHang))
//                .trangThai(Integer.parseInt(trangThai))
//                .ngayTaoTaiKhoan(ngayTaoTaiKhoan)
//                .matKhau(matKhau)
//                .loaiKhachHang(loaiKhachHang)
//                .hoVaTen(hoVaTen)
//                .sdt(sdt)
//                .ngaySinh(ngaySinh)
//                .gioiTinh(Integer.parseInt(gioiTinh))
//                .email(email)
//                .build();
//        this.iKhachHangService.save(khachupdate);
//        return "redirect:/quan-ly/khach-hang/hien-thi";
//    }

}
