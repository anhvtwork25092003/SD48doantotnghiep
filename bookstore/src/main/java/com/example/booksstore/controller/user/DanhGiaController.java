package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IDanhGiarepository;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.IKiemTraDanhGiaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

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
                DanhGia danhGiaSaved = this.iDanhGiarepository.save(danhGia);
                // giảm trừ lượt đánh giá
                this.iKiemTraDanhGiaService.giamLuotDanhGia(khachHangDangNhap.getIdKhachHang(), Integer.parseInt(idSach));
                model.addAttribute("thongBaoDanhGia", "Gửi Đánh Giá Thành Công!");
            } else {
                model.addAttribute("thongBaoDanhGia", "Chưa được phép đánh giá sản phẩm này!");
            }
        } else {
            model.addAttribute("thongBaoDanhGia", "Hãy đăng nhập để gửi đánh giá!");
        }
        return "redirect:/trang-chu";
    }

    @GetMapping("/delete/{idDanhGia}")
    public String delete(@PathVariable String idDanhGia,
                         HttpSession session) {
        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        KhachHang khachHang = iKhachHangRepository.findById(khachHangDangNhap.getIdKhachHang()).get();
        // xác minh khách hàng và comment

        boolean ketQuaXacMinh = this.iKiemTraDanhGiaService.xacMinhDanhGiaKhachHang(khachHang.getIdKhachHang(), Integer.parseInt(idDanhGia));
        if (ketQuaXacMinh == true) {
            // chuyển trạng thái về 2;
            

        }

        return "redirect:/trang-chu";
    }
}
