package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.ThongBao;
import com.example.booksstore.entities.ThongTinGiaoHang;
import com.example.booksstore.entities.TraHang;
import com.example.booksstore.entities.TraHangChiTiet;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.repository.ITraHangChiTietRepository;
import com.example.booksstore.repository.ITraHangRepository;
import com.example.booksstore.service.EmailSenderService;
import com.example.booksstore.service.IDonHangService;
import com.example.booksstore.service.IThongBaoService;
import com.example.booksstore.service.ThongBaoKhachHangService;
import com.example.booksstore.service.TraHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
    @Autowired
    ThongBaoKhachHangService thongBaoKhachHangService;
    @Autowired
    IThongBaoService iThongBaoService;
    @Autowired
    private EmailSenderService senderService;

    @GetMapping("/danh-sach-doi/{idDonHang}")
    public String laydanhsachsoluongcothetra(@PathVariable int idDonHang, Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        List<DonHangChiTiet> donHangChiTietList = traHangService.danhSachSanPhamCoTheDoiTra(idDonHang);
        model.addAttribute("thongTinSanPhamList", donHangChiTietList);
        return "/admin/quanly/ChonDoiTra";
    }

    @PostMapping("/tra-hang")
    public String processTraHangForm(
            @RequestParam(name = "idSach") List<Integer> sachIds,
            @RequestParam(name = "idDonHang") List<Integer> idDonHangs,
            @RequestParam(name = "soLuong") List<Integer> soLuongs,
            @RequestParam(name = "loaiTraHang") int loaiTraHang,

            RedirectAttributes redirectAttributes) {

        if (loaiTraHang == 1) {
            //  trả hàng hoàn tiền
            TraHang traHang = new TraHang();
            boolean checksoluong = false;
            for (Integer integer : soLuongs) {
                if (integer != 0) {
                    checksoluong = true;
                }
            }
            if (checksoluong == false) {
                return "redirect:/quan-ly/danh-sach-doitra";
            }
            for (int i = 0; i < sachIds.size(); i++) {
                Integer sachId = sachIds.get(i);
                Integer soLuong = soLuongs.get(i);
                Sach sach = iSachRepository.findById(sachId).orElse(null);
            }

            Date currDate = new Date();
            List<TraHangChiTiet> traHangChiTietList = new ArrayList<>();
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
                    traHang.setLoaiTraHang(1);
                    iTraHangRepository.save(traHang);
                    TraHangChiTiet traHangChiTiet1 = iTraHangChiTietRepository.save(traHangChiTiet);
                    traHangChiTietList.add(traHangChiTiet1);
                }
                ThongTinGiaoHang thongTinGiaoHang = traHang.getDonHang().getThongTinGiaoHang();
                KhachHang khachHangDangNhap = traHang.getDonHang().getKhachHang();
                guiEmailDonHang(traHang, thongTinGiaoHang);
                guiThongBaoDonHang(traHang, thongTinGiaoHang, khachHangDangNhap);
            }

            return "redirect:/quan-ly/danh-sach-doitra";
        } else {
            TraHang traHang = new TraHang();
            boolean checksoluong = false;
            for (Integer integer : soLuongs) {
                if (integer != 0) {
                    checksoluong = true;
                }
            }
            if (checksoluong == false) {
                return "redirect:/quan-ly/danh-sach-doitra";
            }
            for (int i = 0; i < sachIds.size(); i++) {
                Integer sachId = sachIds.get(i);
                Integer soLuong = soLuongs.get(i);
                Sach sach = iSachRepository.findById(sachId).orElse(null);

                if (sach.getSoLuongTonKho() < soLuong) {
                    redirectAttributes.addFlashAttribute("soLuongHoanTraKhongDuo", "Số Lượng của " + sach.getTenSach() + " không đủ!");
                    return "redirect:/quan-ly/danh-sach-doitra";
                }
            }

            Date currDate = new Date();
            List<TraHangChiTiet> traHangChiTietList = new ArrayList<>();
            for (int i = 0; i < sachIds.size(); i++) {
                Integer sachId = sachIds.get(i);
                Integer idDonHang = idDonHangs.get(i);
                Integer soLuong = soLuongs.get(i);

                if (soLuong != 0) {
                    Sach sach = iSachRepository.findById(sachId).orElse(null);
                    if (soLuong > sach.getSoLuongTonKho()) {

                    }
                    DonHang donHang = iDonHangRepo.findById(idDonHang).orElse(null);
                    TraHangChiTiet traHangChiTiet = new TraHangChiTiet();
                    traHangChiTiet.setSach(sach);
                    traHangChiTiet.setSoLuong(soLuong);
                    traHangChiTiet.setTraHang(traHang);
                    traHang.setDonHang(donHang);
                    traHang.setNgayTao(currDate);
                    traHang.setTrangThai(0);
                    iTraHangRepository.save(traHang);
                    TraHangChiTiet traHangChiTiet1 = iTraHangChiTietRepository.save(traHangChiTiet);
                    traHangChiTietList.add(traHangChiTiet1);
                }
                ThongTinGiaoHang thongTinGiaoHang = traHang.getDonHang().getThongTinGiaoHang();
                KhachHang khachHangDangNhap = traHang.getDonHang().getKhachHang();
                guiEmailDonHang(traHang, thongTinGiaoHang);
                guiThongBaoDonHang(traHang, thongTinGiaoHang, khachHangDangNhap);
            }

            if (traHangChiTietList != null && traHangChiTietList.size() > 0) {
                for (TraHangChiTiet traHangChiTiet : traHangChiTietList) {
                    Sach sach = iSachRepository.findById(traHangChiTiet.getSach().getIdSach()).orElse(null);
                    int soLuongmoi = sach.getSoLuongTonKho() - traHangChiTiet.getSoLuong();
                    sach.setSoLuongTonKho(soLuongmoi);
                    iSachRepository.save(sach);
                }
            }
            return "redirect:/quan-ly/danh-sach-doitra";
        }
    }


    // hủy don hang
    @GetMapping("/tra-hang/huy-doi-hang/{id}")
    public String huydoihang(@PathVariable("id") String idTraHang) {
        TraHang traHang = this.iTraHangRepository.findById(Integer.parseInt(idTraHang)).get();
        if (traHang == null) {
            return "redirect:/quan-ly/danh-sach-doitra";
        } else {
            traHang.setTrangThai(3);
            this.iTraHangRepository.save(traHang);
            if (traHang.getLoaiTraHang() == 0) {
//               đổi hàng, tăng lại soosluowngj hàng nếu hủy
                for (TraHangChiTiet traHangChiTiet : traHang.getTraHangChiTietList()) {
                    Sach sach = iSachRepository.findById(traHangChiTiet.getSach().getIdSach()).get();
                    int soLuongMoi = sach.getSoLuongTonKho() + traHangChiTiet.getSoLuong();
                    sach.setSoLuongTonKho(soLuongMoi);
                    this.iSachRepository.save(sach);
                }
            }

        }
        return "redirect:/quan-ly/danh-sach-doitra";
    }

    @PostMapping("/tim-kiem-id-doi-hang")
    public String searchDaDuyet(@RequestParam("idTraHang") Integer idTraHang,
                                HttpSession session,
                                Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> timKiemMaAndSdt = iTraHangRepository.findAllByIdTraHang(pageable, idTraHang);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("listth", timKiemMaAndSdt);
        model.addAttribute("");
        System.out.println("Aaaaaaaaaaa" + timKiemMaAndSdt);
        return "admin/quanly/DoiHang";
    }
    @PostMapping("/tim-kiem-id-doi-hang-van-chuyen")
    public String searchDvanchuyen(@RequestParam("idTraHang") Integer idTraHang,
                                HttpSession session,
                                Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> timKiemMaAndSdt = iTraHangRepository.findAllByIdTraHang(pageable, idTraHang);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("listth", timKiemMaAndSdt);
        model.addAttribute("");
        System.out.println("Aaaaaaaaaaa" + timKiemMaAndSdt);
        return "admin/quanly/doihang/doihangdanggiaohang";
    }
    @PostMapping("/tim-kiem-id-doi-hang-thanh-cong")
    public String searchthanhCong(@RequestParam("idTraHang") Integer idTraHang,
                                HttpSession session,
                                Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> timKiemMaAndSdt = iTraHangRepository.findAllByIdTraHang(pageable, idTraHang);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("listth", timKiemMaAndSdt);
        model.addAttribute("");
        System.out.println("Aaaaaaaaaaa" + timKiemMaAndSdt);
        return "admin/quanly/doihang/donhanggiaothanhcong";

    }
    @PostMapping("/tim-kiem-id-doi-hang-k-thanh-cong")
    public String searchKhongThanhCong(@RequestParam("idTraHang") Integer idTraHang,
                                HttpSession session,
                                Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> timKiemMaAndSdt = iTraHangRepository.findAllByIdTraHang(pageable, idTraHang);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("listth", timKiemMaAndSdt);
        model.addAttribute("");
        System.out.println("Aaaaaaaaaaa" + timKiemMaAndSdt);
        return "admin/quanly/doihang/huydoihang";
    }

    @GetMapping("/danh-sach-doitra")
    public String danhSachDonDoiHangDangChuanBiHang(HttpSession session,
                                                    @RequestParam(value = "maDonDoiTra", required = false) String maDondoitra
            , Model model
            , @RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> traHangs = this.iTraHangRepository.findAllByTrangThai(pageable, 0);
        model.addAttribute("listth", traHangs);
        return "admin/quanly/DoiHang";
    }

    @GetMapping("/danh-sach-doitra/dang-van-chuyen")
    public String danhSachDonDoiHangDangVanChuyen(HttpSession session
            , Model model, @RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> traHangs = this.iTraHangRepository.findAllByTrangThai(pageable, 1);
        model.addAttribute("listth", traHangs);
        return "admin/quanly/doihang/doihangdanggiaohang";
    }

    @GetMapping("/danh-sach-doitra/van-chuyen-thanh-cong")
    public String danhSachDonDoiHangVanChuyenThanhCong(HttpSession session
            , Model model, @RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> traHangs = this.iTraHangRepository.findAllByTrangThai(pageable, 2);
        model.addAttribute("listth", traHangs);
        return "admin/quanly/doihang/donhanggiaothanhcong";
    }

    @GetMapping("/danh-sach-doitra/khong-thanh-cong")
    public String danhSachDonDoiHoanTra(HttpSession session
            , Model model, @RequestParam(defaultValue = "1") int page) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<TraHang> traHangs = this.iTraHangRepository.findAllByTrangThai(pageable, 3);
        model.addAttribute("listth", traHangs);
        return "admin/quanly/doihang/huydoihang";
    }

    @GetMapping("/danh-sach-chi-tiet")
    public List<TraHangChiTiet> chitiet(@PathVariable Integer idTraHang) {
        List<TraHangChiTiet> traHangChiTiets = iTraHangChiTietRepository.findAllByTraHang_IdTraHang(idTraHang);
        return traHangChiTiets;
    }


    //     chyển trang thái
    @GetMapping("/xac-nhan-tra-hang-tra")
    public String traDonHangDanggiao(Model model, HttpSession session,
                                     @RequestParam("idTraHang") String idTraHang) {
        TraHang traHang = iTraHangRepository.findById(Integer.parseInt(idTraHang)).get();
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (traHang.getLoaiTraHang() == 1) {
            if (traHang.getTrangThai() == 0) {
                traHang.setTrangThai(2);
                iTraHangRepository.save(traHang);
                model.addAttribute("loggedInUser", nhanVien);
                ThongTinGiaoHang thongTinGiaoHang = traHang.getDonHang().getThongTinGiaoHang();
                KhachHang khachHangDangNhap = traHang.getDonHang().getKhachHang();
                guiEmailDonHang(traHang, thongTinGiaoHang);
                guiThongBaoDonHang(traHang, thongTinGiaoHang, khachHangDangNhap);
            }
        }
        if (traHang.getTrangThai() == 0) {
            traHang.setTrangThai(1);
            iTraHangRepository.save(traHang);
            System.out.println("Đã xác nhận và cập nhật trạng thái trả hàng: " + traHang);
            model.addAttribute("loggedInUser", nhanVien);
            ThongTinGiaoHang thongTinGiaoHang = traHang.getDonHang().getThongTinGiaoHang();
            KhachHang khachHangDangNhap = traHang.getDonHang().getKhachHang();
            guiEmailDonHang(traHang, thongTinGiaoHang);
            guiThongBaoDonHang(traHang, thongTinGiaoHang, khachHangDangNhap);
            return "redirect:/quan-ly/danh-sach-doitra/dang-van-chuyen";
        } else if (traHang.getTrangThai() == 1) {
            traHang.setTrangThai(2);
            iTraHangRepository.save(traHang);
            System.out.println("Đã xác nhận và cập nhật trạng thái trả hàng: " + traHang);
            model.addAttribute("loggedInUser", nhanVien);
            ThongTinGiaoHang thongTinGiaoHang = traHang.getDonHang().getThongTinGiaoHang();
            KhachHang khachHangDangNhap = traHang.getDonHang().getKhachHang();
            guiEmailDonHang(traHang, thongTinGiaoHang);
            guiThongBaoDonHang(traHang, thongTinGiaoHang, khachHangDangNhap);
            return "redirect:/quan-ly/danh-sach-doitra/van-chuyen-thanh-cong";
        }
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/danh-sach-doitra";
    }

    public void guiEmailDonHang(TraHang traHang, ThongTinGiaoHang thongTinGiaoHang) {
        String trangThaiDonHang = "";
        switch (traHang.getTrangThai()) {
            case 1:
                trangThaiDonHang = "Đơn hàng của bạn đang được vận chuyển!!!!";
                break;
            case 2:
                trangThaiDonHang = "Đơn đổi hàng của bạn đã được hoàn thành!!!!";
                break;
            default:
                if (traHang.getTrangThai() == 0) {
                    trangThaiDonHang = "Đơn đổi/trả hàng của bạn đã đang được chuẩn bị !";
                }
        }

        String subject = "Thông báo về đơn trả hàng! ";
        senderService.sendSimpleEmail(thongTinGiaoHang.getEmailGiaoHang(), subject,
                "Mã Đơn Hàng của bạn " + traHang.getDonHang().getMaDonHang() + "\n" +
                        "Trạng thái đơn: " + trangThaiDonHang);
    }


    public void guiThongBaoDonHang(TraHang traHang, ThongTinGiaoHang thongTinGiaoHang, KhachHang khachHang) {
        Date currDate = new Date();
        String trangThaiMessage = "";

        switch (traHang.getTrangThai()) {
            case 1:
                trangThaiMessage = "Đơn hàng của bạn đang được vận chuyển";
                break;
            case 2:
                trangThaiMessage = "Đơn hàng của bạn đã được hoàn thành !!!!";
                break;
            default:
                if (traHang.getTrangThai() == 0) {
                    trangThaiMessage = "Đơn hàng của bạn đã được đổi lại và đang chuẩn bị hàng!!!\"";
                }
        }

        String noiDung = "Thông Báo về đơn hàng " + traHang.getDonHang().getMaDonHang() + "\n" + trangThaiMessage + "\n"
                + "Cảm ơn đã mua hàng!";

        ThongBao thongBao = new ThongBao();
        thongBao.setNgayGui(currDate);
        thongBao.setNoiDung(noiDung);

        // lưu thông báo vào db
        ThongBao savedNotification = this.iThongBaoService.createNew(thongBao);

        // gọi hàm
        this.thongBaoKhachHangService.themThongBaoChoNguoiDung(khachHang.getIdKhachHang(), savedNotification);
    }
}
