package com.example.booksstore.controller.user;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("danh-sach-tim-kiem")
public class TimKiemController {
    @Autowired
    ISachService iSachService;
    @Autowired
    ISachRepository repository;
    @Autowired
    TacGiaService tacGiaService;
    @Autowired
    ITheLoaiServiec iTheLoaiService;

    @GetMapping("/search")
    public String searchBooks(@RequestParam("tenSach") String tenSach, Model model) {
        List<Sach> books = repository.findByTenSachContaining(tenSach);
        model.addAttribute("sachmoi", books);
        return "user/DanhSachSanPhamTimKiem";
    }

}