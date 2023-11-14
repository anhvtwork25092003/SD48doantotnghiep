package com.example.booksstore.service;

import com.example.booksstore.entities.ThongBao;

public interface IThongBaoService {

    ThongBao createNew(ThongBao newThongBao);

    void guiYhongBaoChoToanBoKhachHangQuaQuahomThuTrangWeb(int IdThongBao);
}
