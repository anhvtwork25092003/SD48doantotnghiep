package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.repository.TacGiaRepository;
import com.example.booksstore.service.TacGiaService;
import com.example.booksstore.specification.TacGiaSpectification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Repository
public class TacGiaServiceImpl implements TacGiaService {
    @Autowired
    TacGiaRepository re;

    @Override
    public Page<TacGia> pageOfTacGia(Pageable pageable) {
        return re.findAll(pageable);
    }

    @Override
    public TacGia createTacGia(TacGia tacgia) {
        re.save(tacgia);
        return tacgia;
    }

    @Override
    public TacGia GetTacGiaByID(Integer id) {
        return re.findTacGiaByID(id);
    }

    @Override
    public void delete(TacGia tacGia) {
        re.delete(tacGia);
    }

    @Override
    public List<TacGia> findAllTacGia() {
        return re.findAll();
    }

    @Override
    public TacGia UpdateTacGia(Integer id, TacGia tacGia) {
        TacGia tacgia1 = re.findTacGiaByID(id);
        tacgia1.setHoVaTen(tacGia.getHoVaTen());
        tacgia1.setEmail(tacGia.getEmail());
        tacgia1.setLinkAnhTacGia(tacGia.getLinkAnhTacGia());
        tacgia1.setSdt(tacGia.getSdt());
        tacgia1.setTrangThai(tacGia.getTrangThai());
        re.save(tacgia1);
        return tacGia;
    }

    @Override
    public Page<TacGia> searchTacGia(String hoVaTen, String email,Integer trangThai,Pageable pageable) {
        Specification<TacGia> spe= TacGiaSpectification.filterTacGia(hoVaTen,email,trangThai);
        return re.findAll(spe,pageable);
    }

    @Override
    public TacGia getOne(int id) {
        return re.findByIdTacGia(id);
    }


}
