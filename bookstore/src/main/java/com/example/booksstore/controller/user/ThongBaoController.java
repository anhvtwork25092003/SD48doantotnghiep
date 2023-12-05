package com.example.booksstore.controller.user;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.ThongBao;
import com.example.booksstore.entities.ThongBaoKhachHang;
import com.example.booksstore.repository.IThongBaoKhachHangRepo;
import com.example.booksstore.repository.IThongBaoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/thong-bao")
public class ThongBaoController {
    @Autowired
    private IThongBaoKhachHangRepo thongBaoKhachHangRepository;
    @Autowired
    private IThongBaoRepository thongBaoRepositoryt;


    @GetMapping("/danh-sach-thong-bao")
    public List<ThongBao> getThongBaoForUser(HttpSession session) {
        try {
            KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
            List<ThongBao> danhSachThongBao = new ArrayList<>();

            if (khachHangDangNhap != null) {
                // đã đăng nhập
                List<ThongBaoKhachHang> thongBaoList =
                        thongBaoKhachHangRepository.findByKhachHangIdKhachHang(khachHangDangNhap.getIdKhachHang());

                for (ThongBaoKhachHang thongBaoKhachHang : thongBaoList) {
                    ThongBao thongBao = new ThongBao();
                    thongBao.setIdThongBao(thongBaoKhachHang.getThongBao().getIdThongBao());
                    thongBao.setNoiDung(thongBaoKhachHang.getThongBao().getNoiDung());
                    thongBao.setNgayGui(thongBaoKhachHang.getThongBao().getNgayGui());
                    danhSachThongBao.add(thongBao);
                }
                if (!danhSachThongBao.isEmpty() && danhSachThongBao.size() > 0) {
                    return danhSachThongBao;
                } else {
                    // Nếu danh sách rỗng, bạn có thể trả về null hoặc throw một ngoại lệ và xử lý nó ở một tầng khác.
                    return null;
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            // Xử lý lỗi và log
            return Collections.emptyList(); // Hoặc trả về danh sách trống tùy thuộc vào yêu cầu cụ thể của bạn
        }
    }

    @GetMapping("/load-thong-bao")
    public List<ThongBao> loadThongBao() {
        return thongBaoRepositoryt.findAll();
    }
}
