package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.ThongBao;
import com.example.booksstore.repository.IKhuyenMaiReporitory;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.IKhuyenMaiService;
import com.example.booksstore.service.IThongBaoService;
import com.example.booksstore.service.ThongBaoKhachHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyKhuyenMaiController {

    @Autowired
    IKhuyenMaiService iKhuyenMaiService;

    @Autowired
    IKhuyenMaiReporitory iKhuyenMaiReporitory;
    @Autowired
    IThongBaoService iThongBaoService;

    @Autowired
    ISachRepository repository;

    @Autowired
    ThongBaoKhachHangService thongBaoKhachHangService;

    @Value("${upload.anhKhuyenMai}")
    private String uploadAnhKhuyenMai;

    @GetMapping("/khuyen-mai/hien-thi")
    public String hienThiTrangKhuyenMai(Model model, @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(required = false) String tenKhuyenMaiTimKiem,
                                        @RequestParam(required = false) String trangThaiTimKiem,
                                        @RequestParam(required = false) String ngayBatDauTimKiem,
                                        @RequestParam(required = false) String ngayKetThucTimKiem,
                                        HttpSession session
    ) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        } else {
            if (nhanVien.getChucVu().equalsIgnoreCase("Nhan vien")) {
                model.addAttribute("loggedInUser", nhanVien);
                return "redirect:/quan-ly/don-hang/cho-xac-nhan";
            } else {
                model.addAttribute("loggedInUser", nhanVien);
            }
        }
        Page<KhuyenMai> khuyenMaiPages;
        int pageSize = 5; // Đặt kích thước trang mặc định
        int trangThai = 0;
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0
        if (tenKhuyenMaiTimKiem != null || trangThaiTimKiem != null) {
            // xử lý trạng thái
            if (trangThaiTimKiem.equals("1")) {
                trangThai = 1;
            } else if (trangThaiTimKiem.equals("0")) {
                trangThai = 0;
            }
            model.addAttribute("trangThai", trangThaiTimKiem);

            Date ngayBatDau = null;
            Date ngayKetThuc = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                if (ngayBatDauTimKiem != null && !ngayBatDauTimKiem.isEmpty()) {
                    ngayBatDau = sdf.parse(ngayBatDauTimKiem);
                }

                if (ngayKetThucTimKiem != null && !ngayKetThucTimKiem.isEmpty()) {
                    ngayKetThuc = sdf.parse(ngayKetThucTimKiem);
                }
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the exception properly in a real-world scenario
            }

//            if (ngayHomNayTimKiem != null && !ngayHomNayTimKiem.isEmpty()) {
//                // Set ngayBatDau and ngayKetThuc as today
//                LocalDate today = LocalDate.now();
//                ngayBatDau = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
//                ngayKetThuc = Date.from(today.atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
//            }


            khuyenMaiPages = iKhuyenMaiService.searchKhuyenMai(tenKhuyenMaiTimKiem, ngayBatDau, ngayKetThuc, trangThai, pageable);
        } else {
            khuyenMaiPages = iKhuyenMaiService.getAllKhuyenMaiTheoTrangThai(pageable, 1);
        }

        model.addAttribute("sachs", repository.findAll());
        model.addAttribute("khuyenMaiPages", khuyenMaiPages);
        return "/admin/quanly/KhuyenMai2";
    }

    @Transactional
    @PostMapping("/khuyen-mai/them-moi")
    public String themMoiKhuyenMai(
            RedirectAttributes redirectAttributes,
            @RequestParam("tenKhuyenMai") String tenKhuyenMai,
            @RequestParam("ngayBatDau") String ngayBatDau,
            @RequestParam("soPhanTramGiamGia") String soPhanTramGiamGia,
            @RequestParam("ngayKetThuc") String ngayKetThuc,
            @RequestParam("trangThai") String trangThai,
            @RequestParam("sachKM") Set<Sach> sachKM,
            @RequestParam("trangThaiHienThi") String trangThaiHienThi,
            @RequestParam("linkBannerKhuyenMai") MultipartFile linkBannerKhuyenMai,
            @RequestParam("linkAnhKhuyenMai") MultipartFile linkAnhKhuyenMai,
            Model model
    ) throws ParseException {
        try {
            String duongDanCotDinh = "/image/anhKhuyenMai/";
            String duongDanLuuAnhBannerKhuyenMai = duongDanCotDinh + linkBannerKhuyenMai.getOriginalFilename();
            String duongDanLuuAnhKhuyenMai = duongDanCotDinh + linkAnhKhuyenMai.getOriginalFilename();

            if (linkBannerKhuyenMai.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnhBannerKhuyenMai = "";
            } else {
                byte[] bytes = linkBannerKhuyenMai.getBytes();
                Path path = Paths.get(uploadAnhKhuyenMai + linkBannerKhuyenMai.getOriginalFilename());
                Files.write(path, bytes);
            }
            if (linkAnhKhuyenMai.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnhKhuyenMai = "";
            } else {
                byte[] bytes2 = linkAnhKhuyenMai.getBytes();
                Path path2 = Paths.get(uploadAnhKhuyenMai + linkAnhKhuyenMai.getOriginalFilename());
                Files.write(path2, bytes2);
            }
            // xử lý Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date dateNgayBatDau = null;
            Date dateNgayKetThuc = null;
            // Thêm cứng giây thành "00"
            ngayBatDau = ngayBatDau + ":00";
            ngayKetThuc = ngayKetThuc + ":00";
            dateNgayBatDau = dateFormat.parse(ngayBatDau);
            dateNgayKetThuc = dateFormat.parse(ngayKetThuc);
            List<String> result = iKhuyenMaiService.layThongTinSachTrongKhuyenMai(sachKM, dateNgayKetThuc, dateNgayBatDau);
            if (result.isEmpty()) {
                // active for add new books
                KhuyenMai khuyenMai = KhuyenMai.builder()
                        .tenKhuyenMai(tenKhuyenMai)
                        .soPhanTramGiamGia(Integer.parseInt(soPhanTramGiamGia))
                        .ngayBatDau(dateNgayBatDau)
                        .ngayKetThuc(dateNgayKetThuc)
                        .trangThai(Integer.parseInt(trangThai))
                        .trangThaiHienThi(Integer.parseInt(trangThaiHienThi))
                        .sachs(sachKM)
                        .linkBannerKhuyenMai(duongDanLuuAnhBannerKhuyenMai)
                        .linkAnhKhuyenMai(duongDanLuuAnhKhuyenMai)
                        .build();
                redirectAttributes.addFlashAttribute("blankError", iKhuyenMaiService.SaveOrUpdateKhuyenMai(khuyenMai));
            } else {
                // co sach bị trùng khuyến mãi, không thêm, quay lại báo lỗi ra
                redirectAttributes.addFlashAttribute("blankError", result);
                System.out.println(result);
            }
            if (tenKhuyenMai.trim().length() == 0 || soPhanTramGiamGia.trim().length() == 0) {
                redirectAttributes.addFlashAttribute("blankError", "Không được để trống thông tin!");
                return "redirect:/quan-ly/khuyen-mai/hien-thi";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // tạo 1 thông báo dựa vào các thông tin của khuyến mãi
        if (Integer.parseInt(trangThaiHienThi) == 1) {
            String noiDungKhuyenMai = "Thông báo về Khuyến Mãi " + tenKhuyenMai + "\n" +
                    " Ngày bắt đầu: " + ngayBatDau + "\n" + " Ngày kết thúc: " + ngayKetThuc + "\n"
                    + "Giảm " + soPhanTramGiamGia + "% cho các mặt hàng áp dụng !";
            Date currentDate = new Date();
            createThongBao(noiDungKhuyenMai, currentDate);
            System.out.println(noiDungKhuyenMai);
        }

        return "redirect:/quan-ly/khuyen-mai/hien-thi";
    }


    @Transactional
    @PostMapping("/khuyen-mai/cap-nhat")
    public String suaKhuyenMai(
            RedirectAttributes redirectAttributes,
            @RequestParam("idKhuyenMai") String idKhuyenMai,
            @RequestParam("tenKhuyenMai") String tenKhuyenMai,
            @RequestParam("soPhanTramGiamGia") String soPhanTramGiamGia,
            @RequestParam("ngayBatDau") String ngayBatDau,
            @RequestParam("ngayKetThuc") String ngayKetThuc,
            @RequestParam("sachKM2") Set<Sach> sachKM2,
            @RequestParam("trangThaiHienThi") String trangThaiHienThi,
            @RequestParam("editlinkBannerKhuyenMai") MultipartFile linkBannerKhuyenMai,
            @RequestParam("editlinkAnhKhuyenMai") MultipartFile linkAnhKhuyenMai,
            @RequestParam("checkthayDoiBannerKhuyenMai") String trangThaiThayDoiBannerKhuyenMai,
            @RequestParam("checkthayDoiAnhKhuyenMai") String trangThaiThayDoiAnhKhuyenMai,
            Model model
    ) throws IOException {

        KhuyenMai khuyenMai = iKhuyenMaiService.getOne1(Integer.parseInt(idKhuyenMai));
        String duongDanCotDinh = "/image/anhKhuyenMai/";
        String duongDanLuuAnhBannerKhuyenMai = "";
        String duongDanLuuAnhKhuyenMai = "";


        // xác định chuyển từ 0 qua 1, xác minh la 0;
        // tạo 1 thông báo dựa vào các thông tin của khuyến mãi
        if (khuyenMai.getTrangThaiHienThi() == 0) {
            if (Integer.parseInt(trangThaiHienThi) == 1) {
                String noiDungKhuyenMai = "Thông báo về Khuyến Mãi " + tenKhuyenMai + "\n" +
                        " Ngày bắt đầu: " + ngayBatDau + "\n" + " Ngày kết thúc: " + ngayKetThuc + "\n"
                        + "Giảm " + soPhanTramGiamGia + "% cho các mặt hàng áp dụng !";
                Date currentDate = new Date();
                createThongBao(noiDungKhuyenMai, currentDate);
                System.out.println(noiDungKhuyenMai);
            }
        }


        if (trangThaiThayDoiBannerKhuyenMai.equalsIgnoreCase("DaThayDoi")) {
            // anh 1 da thay doi, luu lai anh vao  o
            if (linkBannerKhuyenMai.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnhBannerKhuyenMai = "";
            } else {
                byte[] bytes = linkBannerKhuyenMai.getBytes();
                Path path = Paths.get(uploadAnhKhuyenMai + linkBannerKhuyenMai.getOriginalFilename());
                Files.write(path, bytes);
                duongDanLuuAnhBannerKhuyenMai = duongDanCotDinh + linkBannerKhuyenMai.getOriginalFilename();
            }
        } else {
            // anh chua thay doi, lay lai duong dan cu
            duongDanLuuAnhBannerKhuyenMai = khuyenMai.getLinkBannerKhuyenMai();
        }
//        2
        if (trangThaiThayDoiAnhKhuyenMai.equalsIgnoreCase("DaThayDoi")) {
            // anh 1 da thay doi, luu lai anh vao  o
            if (linkAnhKhuyenMai.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnhKhuyenMai = "";
            } else {
                byte[] bytes2 = linkAnhKhuyenMai.getBytes();
                Path path2 = Paths.get(uploadAnhKhuyenMai + linkAnhKhuyenMai.getOriginalFilename());
                Files.write(path2, bytes2);
                duongDanLuuAnhKhuyenMai = duongDanCotDinh + linkAnhKhuyenMai.getOriginalFilename();

            }
        } else {
            // anh chua thay doi, lay lai duong dan cu
            duongDanLuuAnhKhuyenMai = khuyenMai.getLinkAnhKhuyenMai();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dateNgayBatDau = null;
        Date dateNgayKetThuc = null;
        try {
            // lay khuyen mai dang ton tai
            KhuyenMai khuyenMaiGetOne = this.iKhuyenMaiService.getOne(Integer.parseInt(idKhuyenMai)).get();
            // Thêm cứng giây thành "00"
            ngayBatDau = ngayBatDau + ":00";
            ngayKetThuc = ngayKetThuc + ":00";
            dateNgayBatDau = dateFormat.parse(ngayBatDau);
            dateNgayKetThuc = dateFormat.parse(ngayKetThuc);
            List<String> result = iKhuyenMaiService.layThongTinSachTrongKhuyenMaiChoUpdate(sachKM2, dateNgayKetThuc, dateNgayBatDau, Integer.parseInt(idKhuyenMai));
            if (result.isEmpty()) {
                KhuyenMai KMupdate = KhuyenMai.builder()
                        .idKhuyenMai(Integer.parseInt(idKhuyenMai))
                        .soPhanTramGiamGia(Integer.parseInt(soPhanTramGiamGia))
                        .tenKhuyenMai(tenKhuyenMai)
                        .ngayBatDau(dateNgayBatDau)
                        .trangThai(khuyenMaiGetOne.getTrangThai())
                        .ngayKetThuc(dateNgayKetThuc)
                        .trangThaiHienThi(Integer.parseInt(trangThaiHienThi))
                        .sachs(sachKM2)
                        .linkBannerKhuyenMai(duongDanLuuAnhBannerKhuyenMai)
                        .linkAnhKhuyenMai(duongDanLuuAnhKhuyenMai)
                        .build();
                redirectAttributes.addFlashAttribute("blankError", iKhuyenMaiService.SaveOrUpdateKhuyenMai(KMupdate));
                return "redirect:/quan-ly/khuyen-mai/hien-thi";
            } else {
                // co sach bị trùng khuyến mãi, không thêm, quay lại báo lỗi ra
                redirectAttributes.addFlashAttribute("blankError", result);
                System.out.println(result);
            }
            if (tenKhuyenMai.trim().length() == 0 || soPhanTramGiamGia.trim().length() == 0) {
                redirectAttributes.addFlashAttribute("blankError", "Không được để trống thông tin!");
                return "redirect:/quan-ly/khuyen-mai/hien-thi";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần thiết
        }

        return "redirect:/quan-ly/khuyen-mai/hien-thi";
    }

    @Transactional
    @PostMapping("/khuyen-mai/cap-nhat-ngung-hoat-dong")
    public String ngungHoatDongKhuyenMai(@RequestParam("idKhuyenMai") String idKhuyenMai) {

        // laays ra khuyen mai
        KhuyenMai khuyenMai = this.iKhuyenMaiService.getOne(Integer.parseInt(idKhuyenMai)).get();
        if (khuyenMai == null) {
            System.out.println("khong tim thay khuyen mai!");
        } else {
            KhuyenMai khuyenMaiupdated = this.iKhuyenMaiService.updateTrangThai(Integer.parseInt(idKhuyenMai), 0);
        }
        return "redirect:/quan-ly/khuyen-mai/hien-thi";
    }

    @GetMapping("/delete-khuyen-mai/{idKhuyenMai}")
    public String delete(@PathVariable String idKhuyenMai, RedirectAttributes redirectAttributes) {
        try {
            // Thực hiện xóa khuyến mãi ở đây
            KhuyenMai khuyenMai = this.iKhuyenMaiService.getOne(Integer.parseInt(idKhuyenMai)).orElse(null);
            if (khuyenMai != null) {
                if (khuyenMai.getChiTietDonHang() != null && !khuyenMai.getChiTietDonHang().isEmpty()) {
                    // Có đơn hàng chi tiết áp dụng cho khuyến mãi, không thực hiện xóa
                    redirectAttributes.addAttribute("thongBaoXoaKhuyenMai",
                            "Khuyến mãi đã được áp dụng cho đơn hàng, không thể xóa.");
                } else {
                    // Không có đơn hàng chi tiết áp dụng, tiến hành xóa
                    this.iKhuyenMaiReporitory.delete(khuyenMai);
                    redirectAttributes.addAttribute("thongBaoXoaKhuyenMai",
                            "Khuyến mãi đã được xóa thành công.");
                }
            } else {
                redirectAttributes.addAttribute("thongBaoXoaKhuyenMai",
                        "Không tồn tại chương trình khuyến mại có id là: " + idKhuyenMai);
            }
        } catch (DataIntegrityViolationException e) {
            // Xử lý exception khi không thể xóa do khóa ngoại
            redirectAttributes.addAttribute("thongBaoXoaKhuyenMai",
                    "Đã có đơn hàng áp dụng chương trình khuyến mãi này, không thể xóa.");
        }
        return "redirect:/quan-ly/khuyen-mai/hien-thi";
    }


    public ThongBao createThongBao(String noiDungThongBao, Date ngayGuiThongBao) {
        ThongBao thongBaoforCrateNew = ThongBao.builder().noiDung(noiDungThongBao)
                .ngayGui(ngayGuiThongBao)
                .build();
        ThongBao thongBaoCretated = this.iThongBaoService.createNew(thongBaoforCrateNew);
        this.thongBaoKhachHangService.themThongBaoToanBo(thongBaoCretated);
        return thongBaoCretated;
    }
}
