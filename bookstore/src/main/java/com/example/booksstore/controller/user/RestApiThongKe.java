package com.example.booksstore.controller.user;

import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.service.ThongKeService;
import com.example.booksstore.service.serviceimpl.DonHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doanh-thu")
public class RestApiThongKe {

    @Autowired
    IDonHangRepo donHangRepository;

    @Autowired
    ThongKeService thongKeService;

    @GetMapping("/thang-hien-tai")
    public ResponseEntity<Map<String, BigDecimal>> getDoanhThuThangHienTai() {
        // Lấy ngày đầu và ngày cuối của tháng hiện tại
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());

        // Lấy danh sách đơn hàng trong khoảng thời gian đó
        List<DonHang> donHangList = donHangRepository.findByNgayThanhToanBetween(
                Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        // Tính tổng doanh thu cho từng ngày
        Map<String, BigDecimal> doanhThuByNgay = new HashMap<>();
        for (DonHang donHang : donHangList) {
            // Đổi ngày (Date) thành LocalDate
            LocalDate ngayThanhToanLocalDate = donHang.getNgayThanhToan().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Tạo khóa cho map chỉ sử dụng ngày
            String key = ngayThanhToanLocalDate.toString();

            BigDecimal doanhThu = doanhThuByNgay.getOrDefault(key, BigDecimal.ZERO);
            doanhThu = doanhThu.add(donHang.tinhDoanhThu());
            doanhThuByNgay.put(key, doanhThu);
        }

        return ResponseEntity.ok(doanhThuByNgay);
    }

    @GetMapping("/ban-chay-nhat")
    public ResponseEntity<List<TopSanPhamDTO>> getTopSanPhamBanChayNhat() {
        List<TopSanPhamDTO> topSanPhamList = thongKeService.getTopSanPhamBanChayNhat();
        return ResponseEntity.ok(topSanPhamList);
    }
}
