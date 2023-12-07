package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.service.IThongBaoChiTietService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangChiTietService implements IThongBaoChiTietService {
    @Override
    public List<TopSanPhamDTO> getTopSanPhamBanChayNhat() {
        return null;
    }
}
