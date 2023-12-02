package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.repository.IDonHangRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class LichSuDonHangController {
    @Autowired
    IDonHangRepo iDonHangRepo;

    @GetMapping("/lich-su-don-hang/hien-thi")
    public String hienThiTrangDiaChi(Model model ,HttpSession session,
                                     @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        Page<DonHang> danhSachdonHang = iDonHangRepo.findAllByKhachHangOrderByNgayTaoDesc(khachHang, pageable);
        model.addAttribute("donHanglist", danhSachdonHang);
        return "user/lichsudonhang";
    }
    @PostMapping("/tim-kiem-ma")
    public String searchOrders(@RequestParam(defaultValue = "1") int page,@RequestParam("maDonHang") String maDonHang,
                               HttpSession session,
                               Model model) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        List<DonHang> danhSachdonHang = iDonHangRepo.findByMaDonHangContaining(maDonHang);
        model.addAttribute("loggedInUser", khachHang);
        model.addAttribute("donHanglist", danhSachdonHang);
        return "user/lichsudonhang";
    }

    @GetMapping("/xac-nhan-huy-don")
    public String xacNhanDonHangHuy(Model model,HttpSession session,
                                    @RequestParam("idDonHang") String idDonHang,
                                    @RequestParam("ghiChuLyDoDonHang") String ghiChuLyDoDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.getReferenceById(Integer.parseInt(idDonHang));
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 3) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setTrangThai(3); // Đặt trạng thái thành 1 (đã duyệt)
            //lý do đơn hàng hủy
            donHang.setGhiChuLyDoDonHang(ghiChuLyDoDonHang);

            // Lưu thời gian hủy
            Date thoiGianHuy = new Date();
            donHang.setNgayHuy(thoiGianHuy);

            iDonHangRepo.save(donHang);
            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }
        model.addAttribute("loggedInUser", khachHang);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/lich-su-don-hang/hien-thi";
    }

}
