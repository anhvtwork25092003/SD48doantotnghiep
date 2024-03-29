package com.example.booksstore.controller.user;


import com.example.booksstore.entities.GioHang;
import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TemporaryIdGenerator;
import com.example.booksstore.repository.GioHangChiTietReposutory;
import com.example.booksstore.repository.GioHangRepository;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.ISachService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    ISachRepository iSachRepository;

    @Autowired
    GioHangChiTietReposutory gioHangChiTietReposutory;


    @GetMapping("/danh-sach-san-pham")
    public String xemDanhSachSanPhamTrongGioHang(Model model, HttpSession session) {
        // lấy ra khách hàng và giỏ hàng tương ứng
        // láy ra khach hang
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", khachHang);
        if (khachHang == null) {
            // khach hang chua dang nhap
            System.out.println("khach hang chua dang nhap");
            //  laays gior hangf tuwf session
            // neeus session chuwa cos  giỏ hàng thì tạo 1 giỏ hàng vào sesion
            GioHang gioHangtamThoiSesion = (GioHang) session.getAttribute("gioHang");
            if (gioHangtamThoiSesion == null) {
                // Nếu giỏ hàng chưa tồn tại trong session, tạo mới và lưu vào session
                gioHangtamThoiSesion = new GioHang();
                session.setAttribute("gioHang", gioHangtamThoiSesion);
            }
            List<GioHangChiTiet> listSanPhamTrongGioHangTamThoi = (List<GioHangChiTiet>) session
                    .getAttribute("listSanPhamTrongGioHangTamThoi");
            if (listSanPhamTrongGioHangTamThoi == null) {
                // nếu list chưa tồn tại thì tạo mới
                listSanPhamTrongGioHangTamThoi = new ArrayList<>();
            }
            if (listSanPhamTrongGioHangTamThoi != null) {
                for (GioHangChiTiet gioHangChiTiet : listSanPhamTrongGioHangTamThoi) {
                    Sach sach = this.iSachRepository.findByIdSach(gioHangChiTiet.getSach().getIdSach());
                    gioHangChiTiet.setSach(sach);
                }

            }

            session.setAttribute("listSanPhamTrongGioHangTamThoi", listSanPhamTrongGioHangTamThoi);
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
        return "user/cart";
    }

    @PostMapping("/them-san-pham-vao-gio")
    public String themSanPhamVaoGioHang(Model model,
                                        @RequestParam("idSach") String idSachDeThemVaoGio,
                                        @RequestParam("soLuongCanMua") String soLuongThem,
                                        HttpSession session) {
        // xác minh đăng nhập
        // chưa đăng nhập thì thực hiện trên list lấy từ sesion
        // nếu đã đăng nhập thực hiện trực tiếp với database
        // b1: xác minh đăng nhập
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        if (khachHang == null) {
            // chua dang nhap
            // lấy list được lưu ở sesion ra
            List<GioHangChiTiet> listSanPhamTrongGioHangTamThoi = (List<GioHangChiTiet>) session
                    .getAttribute("listSanPhamTrongGioHangTamThoi");

            //  thực hiện các thao tác với list
            // kiểm tra trong list đã có sách thêm vào chưa
            if (listSanPhamTrongGioHangTamThoi == null) {
                listSanPhamTrongGioHangTamThoi = new ArrayList<>();
                session.setAttribute("listSanPhamTrongGioHangTamThoi", listSanPhamTrongGioHangTamThoi);
            }
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
                chiTietMoi.setIdGioHangChiTiet(TemporaryIdGenerator.generateTemporaryId());
                chiTietMoi.setSach(this.iSachService.getOne(Integer.parseInt(idSachDeThemVaoGio)));
                chiTietMoi.setSoLuong(Integer.parseInt(soLuongThem));
                chiTietMoi.setNgayChinhSua(currentDate);
                listSanPhamTrongGioHangTamThoi.add(chiTietMoi);
            }
            // lưu lại session
            session.setAttribute("listSanPhamTrongGioHangTamThoi", listSanPhamTrongGioHangTamThoi);

        } else {
            // da dang nhap
            boolean daCoTrongGioHang = false;
            GioHang gioHang = this.gioHangRepository.findByKhachHang(khachHang);
            if (gioHang == null) {
                GioHang gioHangmoi = GioHang.builder().khachHang(khachHang).build();
                gioHang = this.gioHangRepository.save(gioHangmoi);
            }
            List<GioHangChiTiet> danhSachSanPhamTrongGioHangCuaKhachDaDangnhap = this.gioHangChiTietReposutory.findAllByGioHang(gioHang);
            if (danhSachSanPhamTrongGioHangCuaKhachDaDangnhap == null) {
                Date currentDate = new Date();
                GioHangChiTiet chiTietMoi = new GioHangChiTiet();
                chiTietMoi.setSach(this.iSachService.getOne(Integer.parseInt(idSachDeThemVaoGio)));
                chiTietMoi.setSoLuong(Integer.parseInt(soLuongThem));
                chiTietMoi.setNgayChinhSua(currentDate);
                chiTietMoi.setGioHang(gioHang);
                this.gioHangChiTietReposutory.save(chiTietMoi);

            }
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
                chiTietMoi.setGioHang(gioHang);
                this.gioHangChiTietReposutory.save(chiTietMoi);
            }
        }
        return "redirect:/gio-hang/danh-sach-san-pham";
    }

    @GetMapping("/xoa-san-pham-khoi-gio")
    public String xoaSanPhamKhoiGioHang(Model model,
                                        @RequestParam("idGioHangChiTiet") String idGioHangChiTiet,
                                        @RequestParam("idSachgh") String idSachgh,
                                        HttpSession session) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", khachHang);

        if (khachHang == null) {
            // Đối với người dùng chưa đăng nhập, thực hiện trên danh sách trong session
            List<GioHangChiTiet> listSanPhamTrongGioHangTamThoi = (List<GioHangChiTiet>) session
                    .getAttribute("listSanPhamTrongGioHangTamThoi");

            if (listSanPhamTrongGioHangTamThoi != null && !listSanPhamTrongGioHangTamThoi.isEmpty()) {
                // Tìm và xóa sản phẩm từ danh sách
                listSanPhamTrongGioHangTamThoi.removeIf(gioHangChiTiet ->
                        gioHangChiTiet.getSach().getIdSach() == Integer.parseInt(idSachgh));
                session.setAttribute("listSanPhamTrongGioHangTamThoi", listSanPhamTrongGioHangTamThoi);
            }

        } else {
            // Đối với người dùng đã đăng nhập, thực hiện trực tiếp trên cơ sở dữ liệu
            GioHang gioHang = this.gioHangRepository.findByKhachHang(khachHang);

            if (gioHang != null) {
                // Tìm và xóa sản phẩm từ giỏ hàng chi tiết trong cơ sở dữ liệu
                GioHangChiTiet gioHangChiTiet = gioHangChiTietReposutory.findById(Integer.parseInt(idGioHangChiTiet)).orElse(null);

                if (gioHangChiTiet != null && gioHangChiTiet.getGioHang().getIdGioHang() == gioHang.getIdGioHang()) {
                    gioHangChiTietReposutory.delete(gioHangChiTiet);
                }
            }
        }

        return "redirect:/gio-hang/danh-sach-san-pham";
    }

    @PostMapping("/cap-nhat-luong-san-pham")
    public String tangSoLuongSanPham(Model model,
                                     @RequestParam("idGioHangChiTiet") String idGioHangChiTiet,
                                     @RequestParam("soLuongSach") String soLuongSach,
                                     @RequestParam("idSachgh") String idSachgh,
                                     HttpSession session) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");

        if (khachHang == null) {
            // Đối với người dùng chưa đăng nhập, thực hiện trên danh sách trong session
            List<GioHangChiTiet> listSanPhamTrongGioHangTamThoi = (List<GioHangChiTiet>) session
                    .getAttribute("listSanPhamTrongGioHangTamThoi");

            if (listSanPhamTrongGioHangTamThoi != null && !listSanPhamTrongGioHangTamThoi.isEmpty()) {
                // Tìm sản phẩm trong danh sách và tăng số lượng
                for (GioHangChiTiet gioHangChiTiet : listSanPhamTrongGioHangTamThoi) {
                    if (gioHangChiTiet.getSach().getIdSach() == Integer.parseInt(idSachgh)) {

                        gioHangChiTiet.setSoLuong(Integer.parseInt(soLuongSach));
                        break;
                    }
                }
                session.setAttribute("listSanPhamTrongGioHangTamThoi", listSanPhamTrongGioHangTamThoi);
            }

        } else {
            // Đối với người dùng đã đăng nhập, thực hiện trực tiếp trên cơ sở dữ liệu
            GioHang gioHang = this.gioHangRepository.findByKhachHang(khachHang);

            if (gioHang != null) {
                // Tìm sản phẩm trong giỏ hàng chi tiết trong cơ sở dữ liệu và tăng số lượng
                GioHangChiTiet gioHangChiTiet = gioHangChiTietReposutory.findById(Integer.parseInt(idGioHangChiTiet)).orElse(null);

                if (gioHangChiTiet != null && gioHangChiTiet.getGioHang().getIdGioHang() == gioHang.getIdGioHang()) {
                    gioHangChiTiet.setSoLuong(Integer.parseInt(soLuongSach));
                    gioHangChiTietReposutory.save(gioHangChiTiet);
                }
            }
        }

        return "redirect:/gio-hang/danh-sach-san-pham";
    }


}
