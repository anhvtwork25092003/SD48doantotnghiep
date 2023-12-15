package com.example.booksstore.controller;

import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.service.TraHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestValidate {
    @Autowired
    TraHangService traHangService;

    @GetMapping
    public List<String> laydanhsachsoluongcothetra() {
        List<String> ketqua = new ArrayList<String>();
        List<DonHangChiTiet> donHangChiTietList = new ArrayList<>();
        donHangChiTietList = this.traHangService.danhSachSanPhamCoTheDoiTra(1063);
        for (DonHangChiTiet donHangChiTiet : donHangChiTietList) {
            ketqua.add(donHangChiTiet.getSoLuong().toString());
        }
        return ketqua;
    }


}
