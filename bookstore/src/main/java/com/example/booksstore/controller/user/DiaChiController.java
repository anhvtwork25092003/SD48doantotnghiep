package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IDiaChiRepository;
import com.example.booksstore.repository.IKhachHangRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/thanh-toan")
@Controller
// các controller trong này phục vụ cho thanh toán, thế anh khỏi quan tâm
public class DiaChiController {

    @Autowired
    IDiaChiRepository iDiaChiRepository;

    @Autowired
    IKhachHangRepository iKhachHangRepository;

    @PostMapping("/them-dia-chi")
    public String themDiaChi(HttpSession session,
                             @RequestParam("tinhThanhPhoThemMoi") String idTinhThanhPho,
                             @RequestParam("huyenQuanThemMoi") String huyenQuanThemMoi,
                             @RequestParam("xaPhuongThemMoi") String xaPhuongThemMoi,
                             @RequestParam("diaChiCuTheThemMoi") String diaChiCuTheThemMoi,
                             @RequestParam("diaChiChu") String diaChiChu,
                             @RequestParam("TenNguoiNhanThemMoi") String TenNguoiNhanThemMoi,
                             @RequestParam("sdtnhanHangThemMoi") String sdtnhanHangThemMoi

    ) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        DiaChi diaChi = DiaChi.builder()
                .tinhThanhPho(idTinhThanhPho)
                .huyenQuan(huyenQuanThemMoi)
                .xaPhuong(xaPhuongThemMoi)
                .diaChiCuThe(diaChiCuTheThemMoi)
                .diaChiChu(diaChiChu)
                .khachHangDiaChi(khachHang)
                .tenNguoiNhan(TenNguoiNhanThemMoi)
                .sdtNguoiNhanHang(sdtnhanHangThemMoi)
                .build();
        DiaChi diaChiMoiThem = this.iDiaChiRepository.save(diaChi);
        KhachHang khachHangCapNhat = this.iKhachHangRepository.findById(khachHang.getIdKhachHang()).get();
        session.setAttribute("loggedInUser", khachHangCapNhat);
        List<String> selectedValues = (List<String>) session.getAttribute("selectedValues");

        StringBuilder redirectUrl = new StringBuilder("redirect:/thanh-toan/xac-nhan-thanh-toan?");

        // Process each value in the list
        for (String selectedValue : selectedValues) {
            // Convert each value to an integer
            int idGioHangChiTiet = Integer.parseInt(selectedValue);

            // Append the idGioHangChiTiet to the redirect URL
            redirectUrl.append("selectedValues=").append(idGioHangChiTiet).append("&");
        }

        // Remove the trailing "&" from the URL
        redirectUrl.deleteCharAt(redirectUrl.length() - 1);

        // Perform the redirect after the loop
        return redirectUrl.toString();

    }

    @GetMapping("/xoa-dia-chi/{idDiaChiDelete}")
    public String xoaDiaChi(HttpSession session,
                            @PathVariable int idDiaChiDelete) {
        DiaChi diaChiforDelete = this.iDiaChiRepository.findById(idDiaChiDelete).orElse(null);

        if (diaChiforDelete != null) {
            this.iDiaChiRepository.delete(diaChiforDelete);
        }

        // Extract the selectedValues from the session
        List<String> selectedValues = (List<String>) session.getAttribute("selectedValues");

        // Construct the redirect URL with the selectedValues
        StringBuilder redirectUrl = new StringBuilder("redirect:/thanh-toan/xac-nhan-thanh-toan?");
        if (selectedValues != null) {
            for (String selectedValue : selectedValues) {
                redirectUrl.append("selectedValues=").append(selectedValue).append("&");
            }
            // Remove the trailing "&" from the URL
            redirectUrl.deleteCharAt(redirectUrl.length() - 1);
        }

        return redirectUrl.toString();
    }

}
