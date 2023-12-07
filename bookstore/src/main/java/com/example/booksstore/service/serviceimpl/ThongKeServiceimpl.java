package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.dto.ThongKeKhachHangResponse;
import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class ThongKeServiceimpl implements ThongKeService {

    @Autowired
    IDonHangRepo iDonHangRepo;

    @Autowired
    DonHangChiTietRepo donHangChiTietRepo;

    @Autowired
    IKhachHangRepository iKhachHangRepository;

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
        List<TopSanPhamDTO> topSanPhamDTOS = donHangChiTietRepo.findTop10Products();

        for (TopSanPhamDTO topSanPhamDTO : topSanPhamDTOS) {
            System.out.println(topSanPhamDTO.getTenSanPham());
        }
        return donHangChiTietRepo.findTop10Products();
    }

    @Override
    public ThongKeKhachHangResponse tinhTongSoLuongKhachHangMoi() {
        ThongKeKhachHangResponse thongKeKhachHangResponse = new ThongKeKhachHangResponse();
        LocalDate currentDate = LocalDate.now();

        // Tìm ngày đầu tiên của tuần
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endOfWeekDateTime = startOfWeek.plusDays(6).atTime(LocalTime.MAX);

        Date startOfWeekDate = java.sql.Timestamp.valueOf(startOfWeekDateTime);
        Date endOfWeekDate = java.sql.Timestamp.valueOf(endOfWeekDateTime);
        List<KhachHang> findAllByNgayTaoTaiKhoanBetween
                = iKhachHangRepository.findAllByNgayTaoTaiKhoanBetween(startOfWeekDate, endOfWeekDate);
        if (findAllByNgayTaoTaiKhoanBetween == null
                || findAllByNgayTaoTaiKhoanBetween.size() == 0
                || findAllByNgayTaoTaiKhoanBetween.isEmpty()) {
            thongKeKhachHangResponse.setTongKhachHang("Chưa có khách hàng mới trong tuần vừa qua!");
            thongKeKhachHangResponse.setKhachVangLai("Chưa có khách hàng mới trong tuần vừa qua!");
            thongKeKhachHangResponse.setKhachTaoTaiKhoan("Chưa có khách hàng mới trong tuần vừa qua!");
        } else {
            thongKeKhachHangResponse.setTongKhachHang(
                    "Có " + findAllByNgayTaoTaiKhoanBetween.size() + " khách hàng trong tuần vừa qua!");
            long countOfType0 = findAllByNgayTaoTaiKhoanBetween.stream()
                    .filter(khachHang -> khachHang.getLoaiKhachHang().equals("0"))
                    .count();
            long countOfType1 = findAllByNgayTaoTaiKhoanBetween.stream()
                    .filter(khachHang -> khachHang.getLoaiKhachHang().equals("1"))
                    .count();
            thongKeKhachHangResponse.setKhachVangLai("Có " + countOfType0 + " khách vãng lai");
            thongKeKhachHangResponse.setKhachTaoTaiKhoan("Có " + countOfType1 + " khách hàng mới tạo tài khoản");
        }
        return thongKeKhachHangResponse;
    }
}
