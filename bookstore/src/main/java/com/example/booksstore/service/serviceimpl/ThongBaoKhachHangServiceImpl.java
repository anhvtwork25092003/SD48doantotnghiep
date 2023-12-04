package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.ThongBao;
import com.example.booksstore.entities.ThongBaoKhachHang;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.IThongBaoKhachHangRepo;
import com.example.booksstore.service.ThongBaoKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ThongBaoKhachHangServiceImpl implements ThongBaoKhachHangService {
    @Autowired
    private IThongBaoKhachHangRepo thongBaoKhachHangRepository;

    @Autowired
    private IKhachHangRepository khachHangRepository;

    @Override
    @Transactional
    public void themThongBaoToanBo(ThongBao thongBao) {
        // Lấy danh sách tất cả người dùng từ cơ sở dữ liệu
        List<KhachHang> danhSachNguoiDung = khachHangRepository.findAll();

        // Tạo danh sách ThongBaoKhachHang để thêm vào cùng một lúc
        List<ThongBaoKhachHang> danhSachThongBaoKhachHang = new ArrayList<>();

        // Thêm thông báo cho từng người dùng
        for (KhachHang nguoiDung : danhSachNguoiDung) {
            ThongBaoKhachHang thongBaoKhachHang = new ThongBaoKhachHang();
            thongBaoKhachHang.setKhachHang(nguoiDung);
            thongBaoKhachHang.setThongBao(thongBao);
            danhSachThongBaoKhachHang.add(thongBaoKhachHang);
        }

        // Batch insert
        thongBaoKhachHangRepository.saveAll(danhSachThongBaoKhachHang);
    }

    @Transactional
    public void themThongBaoChoNguoiDung(Integer userId, ThongBao thongBao) {
        // Lấy thông tin người dùng từ userId
        Optional<KhachHang> optionalKhachHang = khachHangRepository.findById(userId);
        // Kiểm tra xem người dùng có tồn tại không
        if (optionalKhachHang.isPresent()) {
            KhachHang nguoiDung = optionalKhachHang.get();
            // Tạo ThongBaoKhachHang mới
            ThongBaoKhachHang thongBaoKhachHang = new ThongBaoKhachHang();
            thongBaoKhachHang.setKhachHang(nguoiDung);
            thongBaoKhachHang.setThongBao(thongBao);

            // Lưu vào cơ sở dữ liệu
            thongBaoKhachHangRepository.save(thongBaoKhachHang);
        } else {
            // Xử lý khi không tìm thấy người dùng
            throw new RuntimeException("Người dùng không tồn tại");
        }
    }
}
