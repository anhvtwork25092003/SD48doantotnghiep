package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.repository.IDanhGiarepository;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.IDanhGiaService;
import com.example.booksstore.service.IKiemTraDanhGiaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/danh-gia")
public class DanhGiaController {

    @Autowired
    ISachRepository iSachRepository;
    @Autowired
    IKiemTraDanhGiaService iKiemTraDanhGiaService;
    @Autowired
    IKhachHangRepository iKhachHangRepository;
    @Autowired
    IDanhGiarepository iDanhGiarepository;
    @Autowired
    IDanhGiaService iDanhGiaService;

    // thêm đánh giá
    @PostMapping("/them-danh-gia")
    public String themDanhGia(HttpSession session,
                              @RequestParam(value = "idSach") String idSach,
                              @RequestParam(value = "mucDanhGia") String mucDanhGia,
                              @RequestParam(value = "binhLuan") String binhLuan,
                              Model model) {
        // lấy id sản phẩm
        // lấy khách hàng
        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        if (khachHangDangNhap != null) {
            // khách hàng logged in
            int idKhachHang = khachHangDangNhap.getIdKhachHang();
            boolean trangThaiDuocPhepDanhGia = iKiemTraDanhGiaService.checkKhaNangDanhGia(idKhachHang, Integer.parseInt(idSach));
            model.addAttribute("khanangdanhgia", trangThaiDuocPhepDanhGia);
            if (trangThaiDuocPhepDanhGia == true) {
                // được đánh giá
                DanhGia danhGia = new DanhGia();
                Date currDate = new Date();
                danhGia.setMucDanhGia(Integer.parseInt(mucDanhGia));
                danhGia.setBinhLuan(binhLuan);
                danhGia.setNgayTao(currDate);
                danhGia.setTrangThai(0);
                danhGia.setSach(iSachRepository.findByIdSach(Integer.parseInt(idSach)));
                danhGia.setKhachHang(iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get());
                this.iDanhGiarepository.save(danhGia);
                // giảm trừ lượt đánh giá
                this.iKiemTraDanhGiaService.giamLuotDanhGia(khachHangDangNhap.getIdKhachHang(), Integer.parseInt(idSach));
                System.out.println(danhGia);
                model.addAttribute("thongBaoDanhGia", "Gửi Đánh Giá Thành Công!");
            } else {
                model.addAttribute("thongBaoDanhGia", "Chưa được phép đánh giá sản phẩm này!");
            }
        } else {
            model.addAttribute("thongBaoDanhGia", "Hãy đăng nhập để gửi đánh giá!");
        }
        return "redirect:/trang-chu/detail?idSach=" + idSach;
    }

//    @GetMapping("/delete/{idDanhGia}")
//    public String delete(@PathVariable String idDanhGia,
//                         HttpSession session) {
//        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
//        KhachHang khachHang = iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get();
//        // xác minh khách hàng và comment
//
//        boolean ketQuaXacMinh = this.iKiemTraDanhGiaService.xacMinhDanhGiaKhachHang(khachHang.getIdKhachHang(), Integer.parseInt(idDanhGia));
//        if (ketQuaXacMinh == true) {
//            // chuyển trạng thái về 2;
//            DanhGia danhGia = this.iDanhGiarepository.findById(Integer.parseInt(idDanhGia)).get();
//            danhGia.setTrangThai(2);
//            DanhGia danhGia1 = this.iDanhGiarepository.save(danhGia);
//        }
//        return "redirect:/trang-chu";
//    }

    @GetMapping("/hien-thi")
    public String getalldanhgia(Model model,HttpSession session,@RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DanhGia> danhGia = this.iDanhGiarepository.findAll(pageable);
        model.addAttribute("listdg", danhGia);
        return "admin/quanly/DanhGia";
    }
    @PostMapping ("/tim-kiem-id")
    public String getdanhgiabyid(Model model,HttpSession session,@RequestParam("idDanhGia") Integer idDanhGia,@RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DanhGia> danhGia = this.iDanhGiarepository.findByIdDanhGia(pageable,idDanhGia);
        model.addAttribute("listdg",danhGia);
        return "admin/quanly/DanhGia";
    }

    @GetMapping("/cap-nhat")
    public String capNhatTrangThai(@RequestParam("idDanhGia") Integer idDanhGia,HttpSession session,Model model) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        this.iDanhGiaService.capnhatdanhgia(idDanhGia);
        return "redirect:/danh-gia/hien-thi";
    }
    @GetMapping("/xoa")
    public String xoaTrangThai(@RequestParam("idDanhGia") Integer idDanhGia,HttpSession session,Model model) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        this.iDanhGiaService.xoadanhgia(idDanhGia);
        return "redirect:/danh-gia/hien-thi";
    }
    @GetMapping("/chua-duyet")
    public String locchuaduyet(HttpSession session,Model model,@RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        int pageSize = 5;
        int trangthai = 0 ;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DanhGia> danhGia = this.iDanhGiarepository.findAllByTrangThai(pageable,trangthai);
        model.addAttribute("listdg",danhGia);
        return "admin/quanly/DanhGia";
    }
    @GetMapping("/da-duyet")
    public String locdaduyet(HttpSession session,Model model,@RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        int pageSize = 5;
        int trangthai = 1 ;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DanhGia> danhGia = this.iDanhGiarepository.findAllByTrangThai(pageable,trangthai);
        model.addAttribute("listdg",danhGia);
        return "admin/quanly/DanhGia";
    }
    @GetMapping("/khong-duyet")
    public String lockhongduyet(HttpSession session,Model model,@RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        int pageSize = 5;
        int trangthai = 2 ;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DanhGia> danhGia = this.iDanhGiarepository.findAllByTrangThai(pageable,trangthai);
        System.out.println(danhGia);
        for (DanhGia danhGia1:danhGia.getContent()){
            System.out.println(danhGia1.getIdDanhGia());
        }
        model.addAttribute("listdg",danhGia);
        return "admin/quanly/DanhGia";
    }
}
