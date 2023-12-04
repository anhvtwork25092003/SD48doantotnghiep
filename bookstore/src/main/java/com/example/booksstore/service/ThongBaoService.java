package com.example.booksstore.service;

import com.example.booksstore.entities.ThongBao;


public interface ThongBaoService {
    void themThongBaoToanBo(ThongBao thongBao);
    void themThongBaoChoNguoiDung(Integer userId, ThongBao thongBao);
}
