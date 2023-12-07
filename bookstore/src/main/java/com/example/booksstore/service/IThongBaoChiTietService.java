package com.example.booksstore.service;

import com.example.booksstore.dto.TopSanPhamDTO;

import java.util.List;

public interface IThongBaoChiTietService {
    List<TopSanPhamDTO> getTopSanPhamBanChayNhat();
}
