package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.service.ITheLoaiServiec;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/the-loai")
public class TheLoaiController {
    @Autowired
    private ITheLoaiServiec theLoaiServiec;

    @GetMapping("/hien-thi")
    public String TheLoai(Model model, @RequestParam(defaultValue = "1") int page,
                          @RequestParam(required = false) String productNameSearch,
                          @RequestParam(required = false) String productStatusSearch,
                          HttpSession session
    ) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("loggedInUser", nhanVien);

        }
        Page<TheLoai> pageOfTheloai;
        int trangThai = 0;
        int pagesize = 5;
        PageRequest pageable = PageRequest.of(page - 1, pagesize);
        if (productNameSearch != null || productStatusSearch != null) {
            if (productStatusSearch.equals("99")) {
                trangThai = -1;
            } else if (productStatusSearch.equals("1")) {
                trangThai = 1;
            } else if (productStatusSearch.equals("0")) {
                trangThai = 0;
            }
            model.addAttribute("trangThai", productStatusSearch);
            pageOfTheloai = theLoaiServiec.searchTheLoai(productNameSearch, trangThai, pageable);
        } else {
            pageOfTheloai = theLoaiServiec.pageOfTheLoaiTheotrangthai(pageable, 1);
        }
        model.addAttribute("data", pageOfTheloai);
        return "/user/theloai/theloai";

    }


    @Transactional
    @PostMapping("/them-moi")
    public String themTheLoai(
            @RequestParam("tenTheLoai") String tentheloai,
            @RequestParam("mota") String mota,
            @RequestParam("trangThai") String trangthai

    ) {
        try {
            TheLoai theLoai = TheLoai.builder()
                    .tenTheLoai(tentheloai)
                    .moTa(mota)
                    .trangThai(Integer.parseInt(trangthai))
                    .build();
            this.theLoaiServiec.creatTheLoai(theLoai);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/the-loai/hien-thi";
    }

    @PostMapping("/cap-nhat")
    public String suatheloai(
            @RequestParam("IdTheLoai") String idTheLoai,
            @RequestParam("tenTheLoai") String tentheloai,
            @RequestParam("moTa") String mota,
            @RequestParam("trangThai") String trangthai

    ) {
        TheLoai theLoaiUpdate = TheLoai.builder()
                .idTheLoai(Integer.parseInt(idTheLoai))
                .tenTheLoai(tentheloai)
                .moTa(mota)
                .trangThai(Integer.parseInt(trangthai))
                .build();
        this.theLoaiServiec.creatTheLoai(theLoaiUpdate);
        return "redirect:/the-loai/hien-thi";
    }

    @Transactional
    @PostMapping("/cap-nhat-ngung-hoat-dong")
    public String ngungHoatDongTheLoai(@RequestParam("idTheLoai") String idTheLoai) {


        TheLoai theLoai = this.theLoaiServiec.getOne(Integer.parseInt(idTheLoai)).get();
        if (theLoai == null) {
            System.out.println("khong tim thay The Loai !");
        } else {
            TheLoai theLoaiupdated = this.theLoaiServiec.updateTrangThai(Integer.parseInt(idTheLoai), 0);
        }
        return "redirect:/the-loai/hien-thi";
    }
}
