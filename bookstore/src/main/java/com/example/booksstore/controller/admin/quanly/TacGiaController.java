package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TacGiaController {
    @Autowired
    TacGiaService ser;

    @GetMapping("/tac-gia/getall")
    public String GetIndex(Model model){
        List<TacGia> t=ser.findAllTacGia();
        model.addAttribute("data",t);
        return "admin/quanly/tacgia/thanh_tacgia";
    }


}
