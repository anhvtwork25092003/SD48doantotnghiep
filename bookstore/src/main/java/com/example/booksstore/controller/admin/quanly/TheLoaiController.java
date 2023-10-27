package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.TheLoaiRepository;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.serviceimpl.TheLoaiServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/the-loai")
public class TheLoaiController {
    @Autowired
    private ITheLoaiServiec theLoaiServiec;

    @GetMapping("/hien-thi")
    public String TheLoai(Model model, @RequestParam(defaultValue = "1") int page){
        int pagesize=3;
        Pageable pageable = PageRequest.of(page-1,pagesize);
        Page<TheLoai> pageOfTheloai = theLoaiServiec.pageOfTheLoai(pageable);
        model.addAttribute("data",pageOfTheloai);
        return "/user/theloai/theloai";

    }
    @Transactional
    @PostMapping("/them-moi")
    public String themTheLoai(
            @RequestParam("tentheloai") String tentheloai,
            @RequestParam("mota") String mota,
            @RequestParam("trangthai") String trangthai

    )
    {try {
        TheLoai theLoai = TheLoai .builder()
                .tenTheLoai(tentheloai)
                .moTa(mota)
                .trangThai(Integer.parseInt(trangthai))
                .build();
        this.theLoaiServiec.creatTheLoai(theLoai);

    }catch (Exception e){
        e.printStackTrace();
    }
       return "redirect:/the-loai/hien-thi";
    }
}
