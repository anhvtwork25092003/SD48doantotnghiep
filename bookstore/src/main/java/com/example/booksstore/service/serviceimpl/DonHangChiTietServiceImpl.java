package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
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

    @Override
    public BigDecimal tinhTongTienDonHangDaTinhVanChuyen(List<GioHangChiTiet> gioHangChiTietList) {
        List<DonHangChiTiet> donHangChiTietListVnpay = new ArrayList<>();
        BigDecimal tongTienhang = BigDecimal.ZERO;
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
            //chuyển đổi giohang chi tiết = > đơn hàng chi tiết
            DonHangChiTiet donHangChiTietDeThemVaoList = new DonHangChiTiet();
            donHangChiTietDeThemVaoList.setSach(gioHangChiTiet.getSach());
            // Số lượng
            donHangChiTietDeThemVaoList.setSoLuong(gioHangChiTiet.getSoLuong());
            // giá gốc
            donHangChiTietDeThemVaoList.setGiaGoc(gioHangChiTiet.getSach().getGiaBan());

            //phần trăm giả giá , lấy từ khuyens mãi
            double soPhanTramGiamGia = 0.00;
            for (KhuyenMai khuyenMai : gioHangChiTiet.getSach().getKhuyenMais()) {
                if (khuyenMai.getTrangThai() == 1) {
                    // khuyến mãi đang được áp dụng!
                    System.out.println(khuyenMai.getSoPhanTramGiamGia());
                    // lây sra số phần trăm giảm giá
                    soPhanTramGiamGia = (double) ((khuyenMai.getSoPhanTramGiamGia()) / 100.0);
                    // khuyến mãi
                    donHangChiTietDeThemVaoList.setKhuyenMai(khuyenMai);
                    break;
                }
            }
            donHangChiTietDeThemVaoList.setPhanTramGiam(soPhanTramGiamGia * 100);

            //đơn giá thời điểm mua- sau khi giảm trừ
            BigDecimal donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan();
            System.out.println("don gia thoi diem mua: " + donGiathoiDiemMua);
            if (soPhanTramGiamGia > 0.00) {
                BigDecimal thanhTienKhuyenMai = gioHangChiTiet.getSach().getGiaBan().multiply(BigDecimal.valueOf(soPhanTramGiamGia));
                donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan().subtract(thanhTienKhuyenMai);
            }
            donHangChiTietDeThemVaoList.setDonGiaThoiDiemMua(donGiathoiDiemMua);

            // thanh tien
            BigDecimal thanhTien = donGiathoiDiemMua.multiply(BigDecimal.valueOf(Double.valueOf(gioHangChiTiet.getSoLuong())));
            donHangChiTietDeThemVaoList.setThanhTien(thanhTien);
            tongTienhang = tongTienhang.add(thanhTien);

            donHangChiTietListVnpay.add(donHangChiTietDeThemVaoList);
        }
        BigDecimal tienVanChuyen = new BigDecimal(50000);
        tongTienhang = tongTienhang.add(tienVanChuyen);
        return tongTienhang;
    }

    @Override
    public BigDecimal tinhTongTienDonHangChuaTinhVanChuyen(List<GioHangChiTiet> gioHangChiTietList) {
        List<DonHangChiTiet> donHangChiTietListVnpay = new ArrayList<>();
        BigDecimal tongTienhang = BigDecimal.ZERO;
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
            //chuyển đổi giohang chi tiết = > đơn hàng chi tiết
            DonHangChiTiet donHangChiTietDeThemVaoList = new DonHangChiTiet();
            donHangChiTietDeThemVaoList.setSach(gioHangChiTiet.getSach());
            // Số lượng
            donHangChiTietDeThemVaoList.setSoLuong(gioHangChiTiet.getSoLuong());
            // giá gốc
            donHangChiTietDeThemVaoList.setGiaGoc(gioHangChiTiet.getSach().getGiaBan());

            //phần trăm giả giá , lấy từ khuyens mãi
            double soPhanTramGiamGia = 0.00;
            for (KhuyenMai khuyenMai : gioHangChiTiet.getSach().getKhuyenMais()) {
                if (khuyenMai.getTrangThai() == 1) {
                    // khuyến mãi đang được áp dụng!
                    System.out.println(khuyenMai.getSoPhanTramGiamGia());
                    // lây sra số phần trăm giảm giá
                    soPhanTramGiamGia = (double) ((khuyenMai.getSoPhanTramGiamGia()) / 100.0);
                    // khuyến mãi
                    donHangChiTietDeThemVaoList.setKhuyenMai(khuyenMai);
                    break;
                }
            }
            donHangChiTietDeThemVaoList.setPhanTramGiam(soPhanTramGiamGia * 100);

            //đơn giá thời điểm mua- sau khi giảm trừ
            BigDecimal donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan();
            System.out.println("don gia thoi diem mua: " + donGiathoiDiemMua);
            if (soPhanTramGiamGia > 0.00) {
                BigDecimal thanhTienKhuyenMai = gioHangChiTiet.getSach().getGiaBan().multiply(BigDecimal.valueOf(soPhanTramGiamGia));
                donGiathoiDiemMua = gioHangChiTiet.getSach().getGiaBan().subtract(thanhTienKhuyenMai);
            }
            donHangChiTietDeThemVaoList.setDonGiaThoiDiemMua(donGiathoiDiemMua);

            // thanh tien
            BigDecimal thanhTien = donGiathoiDiemMua.multiply(BigDecimal.valueOf(Double.valueOf(gioHangChiTiet.getSoLuong())));
            donHangChiTietDeThemVaoList.setThanhTien(thanhTien);
            tongTienhang = tongTienhang.add(thanhTien);

            donHangChiTietListVnpay.add(donHangChiTietDeThemVaoList);
        }
        return tongTienhang;
    }
}
