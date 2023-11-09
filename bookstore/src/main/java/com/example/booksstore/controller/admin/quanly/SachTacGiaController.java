package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.SachTacGia;
import com.example.booksstore.entities.TacGia;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.SachTacGiaService;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SachTacGiaController {

    private final ISachService sare;
    private final TacGiaService tgsr;
    private final SachTacGiaService sachTacGiaService;

    @Autowired
    public SachTacGiaController(ISachService sare, TacGiaService tgsr, SachTacGiaService sachTacGiaService) {
        this.sare = sare;
        this.tgsr = tgsr;
        this.sachTacGiaService = sachTacGiaService;
    }

    @GetMapping("/get_ctsp")
    public String getCtSP(Model model ){
        List<Sach> books = sare.getall();
        List<TacGia> authors = tgsr.findAllTacGia();

        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        return "user/ctsp/thanh_ctsp";
    }

    @GetMapping("/sach-tacgia-getall")
    public String GetAll(Model model  ){
        List<Sach> books = sare.getall();
        List<TacGia> authors = tgsr.findAllTacGia();

        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
       return "redirect:/get_ctsp";
    }
}
