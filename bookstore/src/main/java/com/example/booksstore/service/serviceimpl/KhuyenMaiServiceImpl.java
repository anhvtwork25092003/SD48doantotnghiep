package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.repository.IKhuyenMaiReporitory;
import com.example.booksstore.service.IKhuyenMaiService;
import com.example.booksstore.service.ISachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            khuyenMai2.setTrangThai(trạngThaiUpdate);
            return iKhuyenMaiReporitory.save(khuyenMai2);
        } else {
            throw new RuntimeException("KhuyenMai not found with id: " + IdKhuyenMai);
        }
    }
    @Scheduled(fixedRate = 60000) // Lên lịch chạy mỗi phút (60,000 ms)
    public void updateTrangThai() {
        List<KhuyenMai> khuyenMais = iKhuyenMaiReporitory.findAll();
        for (KhuyenMai khuyenMai : khuyenMais) {
            khuyenMai.updateTrangThai();
        }
        iKhuyenMaiReporitory.saveAll(khuyenMais);
    }
}
