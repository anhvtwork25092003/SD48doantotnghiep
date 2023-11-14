package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.ThongBao;
import com.example.booksstore.repository.IThongBaoRepository;
import com.example.booksstore.service.IThongBaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThongBaoServiceimpl implements IThongBaoService {
    @Autowired
    IThongBaoRepository iThongBaoRepository;

    @Override
    public ThongBao createNew(ThongBao newThongBao) {
        return this.iThongBaoRepository.save(newThongBao);
    }

    @Override
    public void guiYhongBaoChoToanBoKhachHangQuaQuahomThuTrangWeb(int IdThongBao) {
        ThongBao thongBao = this.iThongBaoRepository.findById(IdThongBao).get();

        if(thongBao != null){

        }else{
            System.out.println("khong tim duoc thong bao phu hop voi id dua vao!");
        }

    }
}
