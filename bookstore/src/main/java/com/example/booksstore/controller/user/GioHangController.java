package com.example.booksstore.controller.user;


import com.example.booksstore.entities.GioHang;
import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.GioHangChiTietReposutory;
import com.example.booksstore.repository.GioHangRepository;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.service.ISachService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    IKhachHangRepository iKhachHangRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    ISachService iSachService;

    @Autowired
    GioHangChiTietReposutory gioHangChiTietReposutory;


    @GetMapping("/danh-sach-san-pham")
    public String xemDanhSachSanPhamTrongGioHang(Model model, HttpSession session) {
        // lấy ra khách hàng và giỏ hàng tương ứng
        // láy ra khach hang
        KhachHang khachHang = this.iKhachHangRepository.findById(3).get();
        if (khachHang == null) {
            // khach hang chua dang nhap
            //  laays gior hangf tuwf session
            // neeus session chuwa cos  giỏ hàng thì tạo 1 giỏ hàng vào sesion
            GioHang gioHangtamThoiSesion = (GioHang) session.getAttribute("gioHang");
            List<GioHangChiTiet> listSanPhamTrongGioHangTamThoi = (List<GioHangChiTiet>) session
                    .getAttribute("listSanPhamTrongGioHangTamThoi");

            if (gioHangtamThoiSesion == null) {
                // Nếu giỏ hàng chưa tồn tại trong session, tạo mới và lưu vào session
                gioHangtamThoiSesion = new GioHang();
                session.setAttribute("gioHang", gioHangtamThoiSesion);
            }
            if (listSanPhamTrongGioHangTamThoi.isEmpty()) {
                // nếu list chưa tồn tại thì tạo mới
                listSanPhamTrongGioHangTamThoi = new ArrayList<>();
            }
            model.addAttribute("danhSachSanPhamTrongGioHang", listSanPhamTrongGioHangTamThoi);

        } else {
            // khach hang da dang nhap
            // lấy giỏ hàng  tuong ung
            GioHang gioHang = this.gioHangRepository.findByKhachHang(khachHang);
            // nếu giỏ hàng chưa tồn tại hoặc đã tồn tại
            if (gioHang == null) {
                //  tạo giỏ hàng mới tươnguwngs
                GioHang gioHangForCreateNew = GioHang.builder()
                        .khachHang(khachHang)
                        .build();
                gioHang = this.gioHangRepository.findByKhachHang(khachHang);
            }
            // lấy giỏ hàng chi tiết
            List<GioHangChiTiet> gioHangChiTiet = this.gioHangChiTietReposutory.findAllByGioHang(gioHang);
            model.addAttribute("danhSachSanPhamTrongGioHang", gioHangChiTiet);
        }
        session.setAttribute("message", "test session");
        String message = (String) session.getAttribute("message");
        model.addAttribute("message", "test session");

        return "user/cart";
    }

    @PostMapping("/them-san-pham-vao-gio")
    public String themSanPhamVaoGioHang(Model model,
                                        String idSachDeThemVaoGio,
                                        String soLuongThem,
                                        HttpSession session) {
        // xác minh đăng nhập
        // chưa đăng nhập thì thực hiện trên list lấy từ sesion
        // nếu đã đăng nhập thực hiện trực tiếp với database
        // b1: xác minh đăng nhập
        KhachHang khachHangLuuOSession = (KhachHang) session.getAttribute("KhachHangLogged");
        if (khachHangLuuOSession == null) {
            // chua dang nhap
            // lấy list được lưu ở sesion ra
            List<GioHangChiTiet> listSanPhamTrongGioHangTamThoi = (List<GioHangChiTiet>) session
                    .getAttribute("listSanPhamTrongGioHangTamThoi");
            //  thực hiện các thao tác với list
            // kiểm tra trong list đã có sách thêm vào chưa
            boolean daCoTrongGioHang = false;
            for (GioHangChiTiet gioHangChiTiet : listSanPhamTrongGioHangTamThoi) {
                if (gioHangChiTiet.getSach().getIdSach() == Integer.parseInt(idSachDeThemVaoGio)) {
                    int soLuongCapNhat = gioHangChiTiet.getSoLuong() + Integer.parseInt(soLuongThem);
                    gioHangChiTiet.setSoLuong(soLuongCapNhat);
                    daCoTrongGioHang = true;
                }
            }
            if (!daCoTrongGioHang) {
                Date currentDate = new Date();
                // chua có trong gio hang, thêm vào list
                GioHangChiTiet chiTietMoi = new GioHangChiTiet();
                chiTietMoi.setSach(this.iSachService.getOne(Integer.parseInt(idSachDeThemVaoGio)));
                chiTietMoi.setSoLuong(Integer.parseInt(soLuongThem));
                chiTietMoi.setNgayChinhSua(currentDate);
                listSanPhamTrongGioHangTamThoi.add(chiTietMoi);
            }
            // lưu lại session
        } else {
            // da dang nhap
            boolean daCoTrongGioHang = false;
            GioHang gioHang = this.gioHangRepository.findByKhachHang(khachHangLuuOSession);
            List<GioHangChiTiet> danhSachSanPhamTrongGioHangCuaKhachDaDangnhap = this.gioHangChiTietReposutory.findAllByGioHang(gioHang);
            for (GioHangChiTiet gioHangChiTiet : danhSachSanPhamTrongGioHangCuaKhachDaDangnhap) {
                if (gioHangChiTiet.getSach().getIdSach() == Integer.parseInt(idSachDeThemVaoGio)) {
                    int soLuongCapNhat = gioHangChiTiet.getSoLuong() + Integer.parseInt(soLuongThem);
                    gioHangChiTiet.setSoLuong(soLuongCapNhat);
                    daCoTrongGioHang = true;
                }
            }
            if (!daCoTrongGioHang) {
                Date currentDate = new Date();
                // chua có trong gio hang, thêm vào list
                GioHangChiTiet chiTietMoi = new GioHangChiTiet();
                chiTietMoi.setSach(this.iSachService.getOne(Integer.parseInt(idSachDeThemVaoGio)));
                chiTietMoi.setSoLuong(Integer.parseInt(soLuongThem));
                chiTietMoi.setNgayChinhSua(currentDate);
                this.gioHangChiTietReposutory.save(chiTietMoi);
            }
        }
        return null;
    }
}
