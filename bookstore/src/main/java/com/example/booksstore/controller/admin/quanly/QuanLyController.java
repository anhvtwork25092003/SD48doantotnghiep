package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.service.ThongKeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyController {

    @Autowired
    ThongKeService thongKeService;

    @GetMapping("/tong-quan")
    public String quanLyTongQuan(HttpSession session, Model model) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        } else if (nhanVien.getChucVu().equalsIgnoreCase("Nhan vien")) {
            return "redirect:/quan-ly/sach/hien-thi";
        } else {
            BigDecimal doanhThuNgayHienTai = thongKeService.getDoanhThuNgayHienTai();
            BigDecimal doanhThuNgayVuaQua = thongKeService.getDoanhThuNgayVuaQua();

            String formattedDoanhThuNgayHienTai = formatCurrency(doanhThuNgayHienTai);
            String formattedDoanhThuNgayVuaQua = formatCurrency(doanhThuNgayVuaQua);

            model.addAttribute("doanhThuNgayHienTai", formattedDoanhThuNgayHienTai);
            model.addAttribute("doanhThuNgayVuaQua", formattedDoanhThuNgayVuaQua);
            model.addAttribute("loggedInUser", nhanVien);
            return "/admin/quanly/TongQuanQuanLy";
        }
    }
    private String formatCurrency(BigDecimal amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(amount);
    }


}
