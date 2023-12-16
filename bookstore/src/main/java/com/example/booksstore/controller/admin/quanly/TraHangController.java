package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.*;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.repository.ITraHangChiTietRepository;
import com.example.booksstore.repository.ITraHangRepository;
import com.example.booksstore.service.IDonHangService;
import com.example.booksstore.service.TraHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/quan-ly")
public class TraHangController {

    @Autowired
    IDonHangService donHangService;
    @Autowired
    IDonHangRepo iDonHangRepo;
    @Autowired
    TraHangService traHangService;
    @Autowired
    ISachRepository iSachRepository;
    @Autowired
    ITraHangRepository iTraHangRepository;
    @Autowired
    ITraHangChiTietRepository iTraHangChiTietRepository;

    @GetMapping("/danh-sach-doi/{idDonHang}")
    public String laydanhsachsoluongcothetra(@PathVariable int idDonHang, Model model) {
        List<DonHangChiTiet> donHangChiTietList = traHangService.danhSachSanPhamCoTheDoiTra(idDonHang);
        model.addAttribute("thongTinSanPhamList", donHangChiTietList);
        return "/admin/quanly/ChonDoiTra";
    }

    @PostMapping("/tra-hang")
    public String processTraHangForm(@ModelAttribute TraHang traHang,
                                     @RequestParam(name = "idSach") Integer sachId,
                                     @RequestParam(name = "idDonHang") Integer idDonHang,
                                     @RequestParam(name = "soLuong") Integer soLuong) {
        // Lấy sách từ cơ sở dữ liệu
        Date currDate = new Date();
        Sach sach = iSachRepository.findById(sachId).orElse(null);
        DonHang donHang = iDonHangRepo.findById(idDonHang).orElse(null);
        // Tạo một chi tiết trả hàng mới
        TraHangChiTiet traHangChiTiet = new TraHangChiTiet();
        traHangChiTiet.setSach(sach);
        traHangChiTiet.setSoLuong(soLuong);
        traHangChiTiet.setTraHang(traHang);

        // Thêm chi tiết trả hàng vào danh sách chi tiết trả hàng của trả hàng
        traHang.setDonHang(donHang);
        traHang.setNgayTao(currDate);
        traHang.setTrangThai(0);
        // Lưu trả hàng vào cơ sở dữ liệu
        iTraHangRepository.save(traHang);
        iTraHangChiTietRepository.save(traHangChiTiet);
        return "redirect:/quan-ly/danh-sach-doitra";
    }

    @GetMapping("/danh-sach-doitra")
    public String doitrahang(HttpSession session,Model model,@RequestParam(defaultValue = "1") int page){
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHangChiTiet> traHangChiTiets = this.iTraHangChiTietRepository.findAll(pageable);
        model.addAttribute("listth", traHangChiTiets);
        return "admin/quanly/DoiHang";
    }
}
