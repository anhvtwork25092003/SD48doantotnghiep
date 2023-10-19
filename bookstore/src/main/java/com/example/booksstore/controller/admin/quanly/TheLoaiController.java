package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.TheLoaiRepository;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.serviceimpl.TheLoaiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/the-loai")
public class TheLoaiController {
    @Autowired
    private ITheLoaiServiec theLoaiServiec;

    @GetMapping("/index")
    public String TheLoai(Model model){
        List<TheLoai>getAll=theLoaiServiec.fillAll();
        model.addAttribute("data",getAll);
        return "/user/theloai/theloai";

    }

}
