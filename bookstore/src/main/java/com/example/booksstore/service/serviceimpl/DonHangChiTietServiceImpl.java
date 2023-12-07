package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;

public class DonHangChiTietServiceImpl implements IDonHangService {
    @Autowired
    IDonHangRepo iDonHangRepo;

    @Autowired
    DonHangChiTietRepo donHangChiTietRepo;

    @Autowired
    ISachRepository iSachRepository;

    @Override
    public void truSoLuongTonKho(DonHangChiTiet donHangChiTietDaMuatronghoaDon) {
        int soLuongSachDaMuatrongHoaDon = donHangChiTietDaMuatronghoaDon.getSoLuong();

        Sach sach = donHangChiTietDaMuatronghoaDon.getSach();

        int soLuongSachMoi = sach.getSoLuongTonKho() - soLuongSachDaMuatrongHoaDon;
        sach.setSoLuongTonKho(soLuongSachMoi);
        Sach sachSaved = this.iSachRepository.save(sach);
    }

    @Override
    public void tangSoLuongTonKho(DonHangChiTiet donHangChiTiet) {
        int soLuongSachDaMuatrongHoaDon = donHangChiTiet.getSoLuong();

        Sach sach = donHangChiTiet.getSach();

        int soLuongSachMoi = sach.getSoLuongTonKho() + soLuongSachDaMuatrongHoaDon;
        sach.setSoLuongTonKho(soLuongSachMoi);
        Sach sachSaved = this.iSachRepository.save(sach);
    }
}
