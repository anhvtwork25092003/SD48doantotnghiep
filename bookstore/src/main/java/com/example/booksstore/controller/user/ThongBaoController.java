package com.example.booksstore.controller.user;

import com.example.booksstore.entities.ThongBaoKhachHang;
import com.example.booksstore.repository.IThongBaoKhachHangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/thong-bao")
public class ThongBaoController {
    @Autowired
    private IThongBaoKhachHangRepo thongBaoKhachHangRepository;

    @GetMapping("/danh-sach-thong-bao/{userId}")
    public List<ThongBaoKhachHang> getThongBaoForUser(@PathVariable Long userId) {
        try {
            List<ThongBaoKhachHang> thongBaoList =
                    thongBaoKhachHangRepository.findByKhachHangIdKhachHang(Integer.parseInt(String.valueOf(userId)));
            if (!thongBaoList.isEmpty()) {
                return thongBaoList;
            } else {
                // Nếu danh sách rỗng, bạn có thể trả về null hoặc throw một ngoại lệ và xử lý nó ở một tầng khác.
                return null;
            }
        } catch (Exception e) {
            // Xử lý lỗi và log
            return Collections.emptyList(); // Hoặc trả về danh sách trống tùy thuộc vào yêu cầu cụ thể của bạn
        }
    }


}
