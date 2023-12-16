package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.service.IKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/books/haventsaleoff")
public class RestAPIKhuyenMai {

    @Autowired
    IKhuyenMaiService iKhuyenMaiService;


    @GetMapping
    public List<Sach> sachList(@RequestParam(value = "batdau") String batDau,
                               @RequestParam(value = "ketthuc") String ketThuc
    ) throws ParseException {
        System.out.println(batDau + "và " + ketThuc);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dateNgayBatDau = null;
        Date dateNgayKetThuc = null;
        // Thêm cứng giây thành "00"
        batDau = batDau + ":00";
        ketThuc = ketThuc + ":00";
        dateNgayBatDau = dateFormat.parse(batDau);
        dateNgayKetThuc = dateFormat.parse(ketThuc);
        List<Sach> sachListt = new ArrayList<>();
        List<Sach> sachListtt = iKhuyenMaiService.danhSachSachKhongThamGiaKhuyenMai(dateNgayKetThuc, dateNgayBatDau);
        if (sachListtt != null && sachListtt.size() > 0) {
            return sachListtt;
        } else {
            return sachListt;
        }
    }
}
