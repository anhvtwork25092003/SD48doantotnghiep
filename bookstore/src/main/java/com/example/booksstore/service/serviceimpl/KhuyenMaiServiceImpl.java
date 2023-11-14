package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.IKhuyenMaiReporitory;
import com.example.booksstore.service.IKhuyenMaiService;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.specification.KhuyenMaiSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class KhuyenMaiServiceImpl implements IKhuyenMaiService {

    @Autowired
    IKhuyenMaiReporitory iKhuyenMaiReporitory;
    @Autowired
    ISachService iSachService;

    @Override
    public Page<KhuyenMai> getAllKhuyenMaiTheoTrangThai(Pageable pageable, int trangThai) {
        return this.iKhuyenMaiReporitory.findAllByTrangThaiOrderByIdKhuyenMai(pageable, trangThai);
    }

    @Override
    public Page<KhuyenMai> getAllKhuyenMaiDangHienThi(Pageable pageable, int trangThaiHienThi) {
        return this.iKhuyenMaiReporitory.findAllByTrangThaiHienThiOrderByIdKhuyenMaiDesc(pageable, trangThaiHienThi);
    }

    @Override
    public KhuyenMai SaveOrUpdateKhuyenMai(KhuyenMai khuyenMai) {
        return this.iKhuyenMaiReporitory.save(khuyenMai);
    }

    @Override
    public Map<String, Object> checkPromotion(Long idSach) {
        return null;
    }


    @Override
    public KhuyenMai updateTrangThai(int IdKhuyenMai, int trạngThaiUpdate) {

        Optional<KhuyenMai> khuyenMai = this.iKhuyenMaiReporitory.findById(IdKhuyenMai);

        if (khuyenMai.isPresent()) {
            KhuyenMai khuyenMai2 = khuyenMai.get();
          Date currentDate = new Date();
            khuyenMai2.setNgayKetThuc(currentDate);
            khuyenMai2.setTrangThai(trạngThaiUpdate);
            return iKhuyenMaiReporitory.save(khuyenMai2);
        } else {
            throw new RuntimeException("KhuyenMai not found with id: " + IdKhuyenMai);
        }
    }

    @Override
    public Optional<KhuyenMai> getOne(int IdKhuyenMai) {
        return this.iKhuyenMaiReporitory.findById(IdKhuyenMai);
    }

    @Scheduled(fixedRate = 60000) // Lên lịch chạy mỗi phút (60,000 ms)
    public void updateTrangThai() {
        List<KhuyenMai> khuyenMais = iKhuyenMaiReporitory.findAll();
        for (KhuyenMai khuyenMai : khuyenMais) {
            khuyenMai.updateTrangThai();
        }
        iKhuyenMaiReporitory.saveAll(khuyenMais);
    }

    @Override
    public List<String> layThongTinSachTrongKhuyenMai(Set<Sach> sachs, Date thoigianbatdau, Date thoigianketthuc) {
        System.out.println(" da chay den sv impl");
        List<KhuyenMai> khuyenMais = iKhuyenMaiReporitory
                .findByTimeRange(thoigianketthuc, thoigianbatdau);
        List<String> ketQua = new ArrayList<String>();
        String ketquaDon = "";
        if (khuyenMais.isEmpty()) {
            System.out.println("khong tim duoc khuye mai phu hop voi thoi gian day, lisst ketquar trong");
        } else {
            System.out.println("có khuyễn mãi xuất hiện trong thời gian đưa vào, bắt đầu check sách");
            for (KhuyenMai khuyenMaiss : khuyenMais) {
                for (Sach s : khuyenMaiss.getSachs()) {
                    if (sachs.contains(s)) {
                        ketquaDon = s.getTenSach() + " đã nằm trong khuyến mãi" + khuyenMaiss.getTenKhuyenMai();
                        ketQua.add(ketquaDon);
                    }
                }
            }
        }
        return ketQua;

    }

    @Override
    public List<String> layThongTinSachTrongKhuyenMaiChoUpdate(Set<Sach> sachs, Date thoigianbatdau, Date thoigianketthuc, int IdKhuyenMai) {

        System.out.println(" da chay den sv impl");
        List<KhuyenMai> khuyenMais = iKhuyenMaiReporitory
                .findByTimeRangeUpDate(thoigianketthuc, thoigianbatdau, IdKhuyenMai);
        List<String> ketQua = new ArrayList<String>();
        String ketquaDon = "";
        if (khuyenMais.isEmpty()) {
            System.out.println("khong tim duoc khuye mai phu hop voi thoi gian day, lisst ketquar trong");
        } else {
            System.out.println("có khuyễn mãi xuất hiện trong thời gian đưa vào, bắt đầu check sách");
            for (KhuyenMai khuyenMaiss : khuyenMais) {
                for (Sach s : khuyenMaiss.getSachs()) {
                    if (sachs.contains(s)) {
                        ketquaDon = s.getTenSach() + " đã nằm trong khuyến mãi" + khuyenMaiss.getTenKhuyenMai();
                        ketQua.add(ketquaDon);
                    }
                }
            }
        }
        return ketQua;
    }

    @Override
    public Page<KhuyenMai> searchKhuyenMai(String tenKhuyenMai, Date ngayBatDau, Date ngayKetThuc, Integer trangThai, Pageable pageable) {
        Specification<KhuyenMai> spec = KhuyenMaiSpecification.filterKhuyenMai(tenKhuyenMai, ngayBatDau, ngayKetThuc, trangThai);
        return iKhuyenMaiReporitory.findAll(spec, pageable);
    }

    @Override
    public KhuyenMai getOne1(int id) {
        return iKhuyenMaiReporitory.findByIdKhuyenMai(id);
    }
}
