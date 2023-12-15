package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.TraHang;
import com.example.booksstore.entities.TraHangChiTiet;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.service.TraHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ITraHangServiceimpl implements TraHangService {

    @Autowired
    IDonHangRepo iDonHangRepo;

    @Override
    public List<DonHangChiTiet> danhSachSanPhamCoTheDoiTra(int idDonHang) {
        // tìm đơn hàng
        List<DonHangChiTiet> donHangChiTietListToanBo = new ArrayList<>();
        List<DonHangChiTiet> donHangChiTietCoTheDoi = new ArrayList<>();
        DonHang donHang = this.iDonHangRepo.findById(idDonHang).get();

        if (donHang != null) {
            donHangChiTietListToanBo = donHang.getChiTietDonHang();
        }

        // danh sách hóa đơn trả hàng của đơn hàng ấy
        List<TraHang> traHangList = new ArrayList<>();
        if (donHang != null) {
            traHangList = donHang.getTraHang();
        }

        for (DonHangChiTiet donHangChiTiet : donHangChiTietListToanBo) {
            for (TraHang traHang : traHangList) {
                for (TraHangChiTiet traHangChiTiet : traHang.getTraHangChiTietList()) {
                    if (donHangChiTiet.getSach().getIdSach() == traHangChiTiet.getSach().getIdSach()) {
                        int soLuongMoi = donHangChiTiet.getSoLuong() - traHangChiTiet.getSoLuong();
                        donHangChiTiet.setSoLuong(soLuongMoi);
                    }
                }
            }
        }
        return donHangChiTietListToanBo;
    }
}
