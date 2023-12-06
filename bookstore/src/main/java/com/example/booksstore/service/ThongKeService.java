package com.example.booksstore.service;

import java.math.BigDecimal;
import java.util.Date;

public interface ThongKeService {
    BigDecimal tinhDoanhThuTheoMocThoiGian(Date startTime, Date endTime);

    BigDecimal getDoanhThuNgayHienTai();

    BigDecimal getDoanhThuNgayVuaQua();
}
