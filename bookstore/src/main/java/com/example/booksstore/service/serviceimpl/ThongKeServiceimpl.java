package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class ThongKeServiceimpl implements ThongKeService {

    @Autowired
    IDonHangRepo iDonHangRepo;

    @Override
    public BigDecimal tinhDoanhThuTheoMocThoiGian(Date startTime, Date endTime) {
        List<DonHang> donHangs = iDonHangRepo.findByNgayThanhToanBetween(startTime, endTime);

        // Tính tổng doanh thu từ danh sách đơn hàng
        BigDecimal tongDoanhThu = BigDecimal.ZERO;
        for (DonHang donHang : donHangs) {
            tongDoanhThu = tongDoanhThu.add(donHang.tinhDoanhThu());
        }

        return tongDoanhThu;
    }

    @Override
    public BigDecimal getDoanhThuNgayHienTai() {
// Lấy ngày hiện tại
        Date now = new Date();

// Tạo Calendar và đặt thời gian bằng ngày hiện tại
        Calendar startOfDayCal = Calendar.getInstance();
        startOfDayCal.setTime(now);

// Đặt thời gian thành 0 giờ 0 phút 0 giây
        startOfDayCal.set(Calendar.HOUR_OF_DAY, 0);
        startOfDayCal.set(Calendar.MINUTE, 0);
        startOfDayCal.set(Calendar.SECOND, 0);

// Lấy ra đối tượng Date đại diện cho 0 giờ 0 phút 0 giây
        Date startOfDay = startOfDayCal.getTime();

// Tạo Calendar mới và đặt thời gian bằng ngày hiện tại
        Calendar endOfDayCal = Calendar.getInstance();
        endOfDayCal.setTime(now);

// Đặt thời gian thành 23 giờ 59 phút 59 giây
        endOfDayCal.set(Calendar.HOUR_OF_DAY, 23);
        endOfDayCal.set(Calendar.MINUTE, 59);
        endOfDayCal.set(Calendar.SECOND, 59);

// Lấy ra đối tượng Date đại diện cho 23 giờ 59 phút 59 giây
        Date endOfDay = endOfDayCal.getTime();
        List<DonHang> donHangs = iDonHangRepo.findByNgayThanhToanBetween(startOfDay, endOfDay);
        if (donHangs.size() == 0) {
            System.out.println("khong tim duoc don hang");
        }

        // Tính tổng doanh thu từ danh sách đơn hàng
        BigDecimal tongDoanhThu = BigDecimal.ZERO;
        for (DonHang donHang : donHangs) {
            System.out.println(donHang.getIdDonHang());
            tongDoanhThu = tongDoanhThu.add(donHang.tinhDoanhThu());
        }
        System.out.println(tongDoanhThu);

        return tongDoanhThu;
    }

    @Override
    public BigDecimal getDoanhThuNgayVuaQua() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // Điều chỉnh để lấy ngày hôm qua
        Date ngayHomQua = calendar.getTime();
        List<DonHang> donHangs = iDonHangRepo.findByNgayThanhToan(ngayHomQua);
        // Tính tổng doanh thu từ danh sách đơn hàng
        BigDecimal tongDoanhThu = BigDecimal.ZERO;
        for (DonHang donHang : donHangs) {
            tongDoanhThu = tongDoanhThu.add(donHang.tinhDoanhThu());
        }

        return tongDoanhThu;
    }

    @Override
    public List<DonHang> getDonHangTrongThangHienTai() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Ngày bắt đầu của tháng
        Date startOfMonth = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // Ngày cuối cùng của tháng
        Date endOfMonth = calendar.getTime();

        return iDonHangRepo.findByNgayThanhToanBetween(startOfMonth, endOfMonth);
    }

    @Override
    public List<TopSanPhamDTO> getTopSanPhamBanChayNhat() {
        return iDonHangRepo.findTopSanPhamBanChayNhat();
    }
}
