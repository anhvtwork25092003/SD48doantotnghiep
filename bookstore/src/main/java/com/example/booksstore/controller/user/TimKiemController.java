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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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


//    @GetMapping("/search")
//    public String searchBooks(@RequestParam("tenSach") String tenSach, Model model) {
//        List<Sach> books = repository.findByTenSachContaining(tenSach);
//        model.addAttribute("sachmoi", books);
//        return "user/DanhSachSanPhamTimKiem";
//    }

    @GetMapping("/loc")
    public String hienThiTrangTimKiem(Model model, @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(required = false) String productNameSearch,
                                      @RequestParam(required = false) String priceRangeSearch,
                                      @RequestParam(required = false) Set<TheLoai> categorySearch,
                                      @RequestParam(required = false) String sapXepGia
    ) {
        Page<Sach> pageOfSach;
        BigDecimal giaMin = null;
        BigDecimal giaMax = null;
        int pageSize = 5; // Đặt kích thước trang mặc định
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0
//  moi khoi tao trang
        if (productNameSearch != null || priceRangeSearch != null || categorySearch != null) {
            // xu ly khoang gia
            if (priceRangeSearch != null) {
                if (priceRangeSearch.equals("all")) {
                    giaMin = new BigDecimal(0);
                    giaMax = new BigDecimal("999999999999999999999999");
                }
                if (priceRangeSearch.equals("1")) {
                    giaMin = new BigDecimal(0);
                    giaMax = new BigDecimal("100000");
                }
                if (priceRangeSearch.equals("2")) {
                    giaMin = new BigDecimal("100000");
                    giaMax = new BigDecimal("500000");
                }
                if (priceRangeSearch.equals("3")) {
                    giaMin = new BigDecimal("500000");
                    giaMax = new BigDecimal("99999999999999999999999999");
                }
                model.addAttribute("price", priceRangeSearch);
            }
            if ("asc".equalsIgnoreCase(sapXepGia)) {
                pageable = PageRequest.of(page - 1, pageSize, Sort.by("giaBan").ascending());
            } else if ("desc".equalsIgnoreCase(sapXepGia)) {
                pageable = PageRequest.of(page - 1, pageSize, Sort.by("giaBan").descending());
            } else {
                pageable = PageRequest.of(page - 1, pageSize);
            }
            // xuu ly trang thai

            pageOfSach = iSachService.TimKiemSach(productNameSearch, giaMin, giaMax, categorySearch, sapXepGia, pageable);
        } else {
            pageOfSach = iSachService.pageOfSach(pageable);
        }

        if (categorySearch != null) {
            model.addAttribute("catego", categorySearch);
        } else {
            List<TheLoai> list = new ArrayList<TheLoai>();
            model.addAttribute("catego", list);
        }


        model.addAttribute("pageOfSach", pageOfSach);
        model.addAttribute("authors", tacGiaService.findAllTacGia());
        model.addAttribute("listTheLoai", iTheLoaiService.findAllTheLoai());
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa" + pageOfSach);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa" + tacGiaService.findAllTacGia());
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa" + iTheLoaiService.findAllTheLoai());
        return "user/DanhSachSanPhamTimKiem";

    }

}