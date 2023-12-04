package com.example.booksstore.controller.user;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.ThongBaoKhachHang;
import com.example.booksstore.repository.IThongBaoKhachHangRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/thong-bao")
public class ThongBaoController {
    @Autowired
    private IThongBaoKhachHangRepo thongBaoKhachHangRepository;

    @GetMapping("/danh-sach-thong-bao")
    public List<ThongBaoKhachHang> getThongBaoForUser(HttpSession session) {
        try {
            KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
            if (khachHangDangNhap != null) {
                List<ThongBaoKhachHang> thongBaoList =
                        thongBaoKhachHangRepository.findByKhachHangIdKhachHang(khachHangDangNhap.getIdKhachHang());
                if (!thongBaoList.isEmpty()) {
                    return thongBaoList;
                } else {
                    // Nếu danh sách rỗng, bạn có thể trả về null hoặc throw một ngoại lệ và xử lý nó ở một tầng khác.
                    return null;
                }
            }else{
                return null;
            }

        } catch (Exception e) {
            // Xử lý lỗi và log
            return Collections.emptyList(); // Hoặc trả về danh sách trống tùy thuộc vào yêu cầu cụ thể của bạn
        }
    }

}
