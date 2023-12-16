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
    public String laydanhsachsoluongcothetra(@PathVariable int idDonHang, Model model,HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        List<DonHangChiTiet> donHangChiTietList = traHangService.danhSachSanPhamCoTheDoiTra(idDonHang);
        model.addAttribute("thongTinSanPhamList", donHangChiTietList);
        return "/admin/quanly/ChonDoiTra";
    }

    @PostMapping("/tra-hang")
    public String processTraHangForm(@ModelAttribute TraHang traHang,
                                     @RequestParam(name = "idSach") List<Integer> sachIds,
                                     @RequestParam(name = "idDonHang") List<Integer> idDonHangs,
                                     @RequestParam(name = "soLuong") List<Integer> soLuongs) {
        Date currDate = new Date();

        for (int i = 0; i < sachIds.size(); i++) {
            Integer sachId = sachIds.get(i);
            Integer idDonHang = idDonHangs.get(i);
            Integer soLuong = soLuongs.get(i);

            if (soLuong != 0) {
                Sach sach = iSachRepository.findById(sachId).orElse(null);
                DonHang donHang = iDonHangRepo.findById(idDonHang).orElse(null);

                TraHangChiTiet traHangChiTiet = new TraHangChiTiet();
                traHangChiTiet.setSach(sach);
                traHangChiTiet.setSoLuong(soLuong);
                traHangChiTiet.setTraHang(traHang);

                traHang.setDonHang(donHang);
                traHang.setNgayTao(currDate);
                traHang.setTrangThai(0);
                iTraHangRepository.save(traHang);
                iTraHangChiTietRepository.save(traHangChiTiet);
            }
        }


        return "redirect:/quan-ly/danh-sach-doitra";
    }


    @GetMapping("/danh-sach-doitra")
    public String doitrahang(HttpSession session,Model model,@RequestParam(defaultValue = "1") int page){
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser",nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> traHangs = this.iTraHangRepository.findAll(pageable);
        model.addAttribute("listth", traHangs);
        return "admin/quanly/DoiHang";
    }
    @GetMapping("/danh-sach-chi-tiet")
    public List<TraHangChiTiet> chitiet(@PathVariable Integer idTraHang){
        List<TraHangChiTiet> traHangChiTiets = iTraHangChiTietRepository.findAllByTraHang_IdTraHang(idTraHang);
        return traHangChiTiets;
    }
    @GetMapping("/xac-nhan-tra-hang-tra")
    public String traDonHangDanggiao(Model model, HttpSession session,
                                        @RequestParam("idTraHang") String idTraHang) {
        TraHang traHang = iTraHangRepository.findById(Integer.parseInt(idTraHang)).get();
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (traHang.getTrangThai() == 0) {
            traHang.setTrangThai(1);
            iTraHangRepository.save(traHang);
            System.out.println("Đã xác nhận và cập nhật trạng thái trả hàng: " + traHang);
        }else if(traHang.getTrangThai() == 1){
            traHang.setTrangThai(2);
            iTraHangRepository.save(traHang);
            System.out.println("Đã xác nhận và cập nhật trạng thái trả hàng: " + traHang);
        }
        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/danh-sach-doitra";

    }
}
