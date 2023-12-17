package com.example.booksstore.config;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.NhanVienRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component("OAuth2SuccessHandler")
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private IKhachHangRepository iKhachHangRepository;
    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        // Thông tin lấy được từ GG Authenication
        String email = token.getPrincipal().getAttribute("email");
        String name = token.getPrincipal().getAttribute("name");

        HttpSession session = request.getSession();
        String redirectUrl = "/trang-chu";

        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        KhachHang khachHang = iKhachHangRepository.findByEmail(email);

        if (khachHang != null && khachHang.getTrangThai() == 1) {
            // Đã tồn tại trong hệ thống -> login user đó
            session.setAttribute("loggedInUser", khachHang);
            System.out.println("Đăng nhập KH " + khachHang.toString());

            this.redirectStrategy.sendRedirect(request, response, redirectUrl);
            return;
        }

        if (nhanVien != null && nhanVien.getTrangThai() == 1) {
            // Đã tồn tại trong hệ thống -> login user đó
            session.setAttribute("dangnhapnhanvien", nhanVien);

            System.out.println("Đăng nhập NV  " + nhanVien.toString());
            this.redirectStrategy.sendRedirect(request, response, redirectUrl);
            return;
        }

        // Chưa tồn tại => Add mới
        // Ở đây mặc định sẽ là add vào bảng Khách hàng nhé!

        Date currentDate = new Date();
        KhachHang khachHangMoi = new KhachHang();
        khachHangMoi.setHoVaTen(name);
        khachHangMoi.setEmail(email);
        khachHangMoi.setNgayTaoTaiKhoan(currentDate);
        // Set loại khách hàng
        khachHangMoi.setLoaiKhachHang("1");
        khachHangMoi.setTrangThai(1);

        iKhachHangRepository.save(khachHangMoi);
        this.redirectStrategy.sendRedirect(request, response, redirectUrl);
        return;
    }
}