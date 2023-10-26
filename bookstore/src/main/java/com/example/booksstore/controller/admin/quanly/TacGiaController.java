package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TacGiaController {
    @Autowired
    TacGiaService ser;

    @GetMapping("/tac-gia/getall")
    public String GetIndex(Model model , @RequestParam(defaultValue = "1") int page){
        int pageSize=3;

        Pageable pageable= PageRequest.of(page-1,pageSize);

        Page<TacGia> pageOfTacGia=ser.pageOfTacGia(pageable);

        model.addAttribute("data",pageOfTacGia);
        return "admin/quanly/tacgia/thanh_tacgia";
    }

    @GetMapping("/tac-gia/getcreate")
    private  String getTacGiaCreate(Model model){
        model.addAttribute("tacgia",new TacGia());
        return "admin/quanly/tacgia/thanh_add_tacgia";
    }

    @PostMapping("/tac-gia/add")
    private  String CreateTacgia(TacGia tacgia){
        ser.createTacGia(tacgia);
        return "redirect:/tac-gia/getall";
    }

    @GetMapping("/tac-gia/getct/{ma}")
    private String getCt(@PathVariable("ma") Integer ma, TacGia tacGia, Model model){
        TacGia t=ser.GetTacGiaByID(ma);
        model.addAttribute("tacgia",t);
        return "";
    }
    @PostMapping("/tac-gia/edit/{ma}")
    public String Edit(@PathVariable("ma")Integer ma,TacGia tacGia,Model model){
        TacGia t=ser.UpdateTacGia(ma,tacGia);
        model.addAttribute("tacgia",t);
        return "";
    }




}
