package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.entities.KiemTraDanhGia;
import com.example.booksstore.repository.IDanhGiarepository;
import com.example.booksstore.repository.IKiemTraDanhGiaRepository;
import com.example.booksstore.service.IKiemTraDanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraDanhGiaServiceImpl implements IKiemTraDanhGiaService {

    @Autowired
    IKiemTraDanhGiaRepository iKiemTraDanhGiaRepository;

    @Autowired
    IDanhGiarepository iDanhGiarepository;

    @Override
    public boolean checkKhaNangDanhGia(int idKhachHang, int idSach) {
        KiemTraDanhGia kiemTraDanhGia =
                this.iKiemTraDanhGiaRepository
                        .findKiemTraDanhGiaByKhachHangIdKhachHangAndSachIdSach(idKhachHang, idSach);
        if (kiemTraDanhGia != null) {
            if (kiemTraDanhGia.getSoLanDanhGia() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public void giamLuotDanhGia(int idKhachHang, int idSach) {
        KiemTraDanhGia kiemTraDanhGia =
                this.iKiemTraDanhGiaRepository
                        .findKiemTraDanhGiaByKhachHangIdKhachHangAndSachIdSach(idKhachHang, idSach);
        int soLanDanhGiaBanDau = 0;
        if (kiemTraDanhGia != null) {
            soLanDanhGiaBanDau = kiemTraDanhGia.getSoLanDanhGia();
            soLanDanhGiaBanDau = soLanDanhGiaBanDau - 1;
            kiemTraDanhGia.setSoLanDanhGia(soLanDanhGiaBanDau);
            this.iKiemTraDanhGiaRepository.save(kiemTraDanhGia);
        }
    }

    @Override
    public boolean xacMinhDanhGiaKhachHang(int idKhachHang, int idDanhGia) {

        DanhGia danhGia = this.iDanhGiarepository.findById(idDanhGia).get();
        if (danhGia != null) {
            if (danhGia.getKhachHang().getIdKhachHang() == idKhachHang) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }
}
