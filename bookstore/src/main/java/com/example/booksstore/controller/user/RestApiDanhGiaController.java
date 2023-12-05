package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.repository.IDanhGiarepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class RestApiDanhGiaController {

    @Autowired
    IDanhGiarepository iDanhGiarepository;

    // lấy đánh giá về 1 sách
    // viết thành rest api
    // API để lấy danh sách đánh giá của một sách (sử dụng định danh của sách)
    @GetMapping("/product/{productId}")
    public List<DanhGia> layDanhSachDanhGia(@PathVariable int productId) {
        // Sử dụng repository để lấy danh sách đánh giá của một sách
        List<DanhGia> danhSachDanhGia = iDanhGiarepository.findAllBySachIdSachAndTrangThai(productId, 1);
        if (danhSachDanhGia == null || danhSachDanhGia.size() == 0) {
            return null;
        } else {
            return danhSachDanhGia;
        }
    }
}
