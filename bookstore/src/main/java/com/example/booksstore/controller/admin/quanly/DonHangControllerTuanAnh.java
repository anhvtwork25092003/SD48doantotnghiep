package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.config.PDFExporter;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.ThongBao;
import com.example.booksstore.entities.ThongTinGiaoHang;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.NhanVienRepository;
import com.example.booksstore.service.EmailSenderService;
import com.example.booksstore.service.IDonHangService;
import com.example.booksstore.service.IKiemTraDanhGiaService;
import com.example.booksstore.service.INhanVienService;
import com.example.booksstore.service.IThongBaoService;
import com.example.booksstore.service.ThongBaoKhachHangService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/quan-ly")
public class DonHangControllerTuanAnh {

    @Autowired
    IDonHangRepo iDonHangRepo;

    @Autowired
    IDonHangService donHangService;

    @Autowired
    DonHangChiTietRepo donHangChiTietRepo;

    @Autowired
    INhanVienService iNhanVienService;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    ThongBaoKhachHangService thongBaoKhachHangService;

    @Autowired
    IThongBaoService iThongBaoService;

    @Autowired
    IKhachHangRepository iKhachHangRepository;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private IKiemTraDanhGiaService iKiemTraDanhGiaService;


    //BẮT ĐẦU CỦA ĐƠN HÀNG CHỜ
    @GetMapping("/don-hang/cho-xac-nhan")
    // bắt đầu hiển thị giao diện đơn hàng chờ
    public String quanLyDonHangCho(@RequestParam(defaultValue = "1") int page,
                                   Model model, HttpSession session,
                                   @RequestParam(required = false) String maDonHang,
                                   @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }
        Page<DonHang> pageDonHang;
        int pageSize = 5;
        model.addAttribute("std", "");
        model.addAttribute("end", "");
        model.addAttribute("ma", "");
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if (maDonHang != null) {
            // xử lý trạng thái
            model.addAttribute("std", "");
            model.addAttribute("end", "");
            model.addAttribute("ma", "");
            Date ngayBatDau = null;
            Date ngayKetThuc = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (startDate != null) {
                    ngayBatDau = sdf.parse(String.valueOf(startDate));
                    model.addAttribute("std", startDate);

                }

                if (endDate != null) {
                    ngayKetThuc = sdf.parse(String.valueOf(endDate));
                    model.addAttribute("end", endDate);

                }
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the exception properly in a real-world scenario
            }
            pageDonHang = donHangService.searchDOnHang(maDonHang, ngayBatDau, ngayKetThuc, 0, pageable);
        } else {
            pageDonHang = iDonHangRepo.findAllByTrangThaiOrderByIdDonHangDesc(pageable, 0);
        }

        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", pageDonHang);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangCho";
    }
    // bắt đầu hiển thị giao diện đơn hàng chờ


    @PostMapping("/cap-nhat-nv")
    public String capNhatNhanVien(@RequestParam("idNhanVien") String idNhanVien,
                                  @RequestParam("idDonHang") String idDonHang,
                                  Model model) {
        try {
            // Lấy đơn hàng từ idDonHang
            int donHangId = Integer.parseInt(idDonHang);
            DonHang donHang = iDonHangRepo.findByIdDonHang(donHangId);

            // Tách các giá trị idNhanVien nếu có dấu phẩy
            String[] idNhanVienArray = idNhanVien.split(",");

            // Lặp qua mảng idNhanVienArray và thêm từng nhân viên vào đơn hàng
            for (String id : idNhanVienArray) {
                try {
                    int idNhanVienInt = Integer.parseInt(id);
                    NhanVien nhanVien = iNhanVienService.getOne(idNhanVienInt);
                    donHang.setNhanVien(nhanVien);
                } catch (NumberFormatException e) {
                    System.out.println("Không thể chuyển đổi '" + id + "' thành số nguyên.");
                }
            }

            // Lưu đơn hàng đã được cập nhật
            iDonHangRepo.save(donHang);

            // Thêm thông báo thành công vào model (nếu cần)
            model.addAttribute("successMessage", "Nhân viên đã được thêm vào đơn hàng.");

        } catch (NumberFormatException e) {
            // Xử lý lỗi chuyển đổi số nguyên
            model.addAttribute("errorMessage", "Lỗi: Định dạng số nguyên không hợp lệ.");
        } catch (Exception e) {
            // Xử lý lỗi khác nếu có
            model.addAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }

        // Điều hướng về trang quản lý đơn hàng chờ xác nhận
        return "redirect:/quan-ly/don-hang/cho-xac-nhan";
    }

    @GetMapping("/chuyen-doi-trang-thai-don/{idDonHang}")
    public String chuyendoitrangthai(@PathVariable("idDonHang") int id) {

        DonHang donHang = this.iDonHangRepo.findById(id).get();
        if (donHang.getTrangThai() == 0) {
            donHang.setTrangThai(1);
            this.iDonHangRepo.save(donHang);
            ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
            KhachHang khachHangDangNhap = donHang.getKhachHang();
            String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
            if ("0".equals(loaiKhachHang)) {
                // Loại khách hàng = 0, chỉ gửi email
                guiEmailDonHang(donHang, thongTinGiaoHang);
            } else {
                // Loại khách hàng = 1, gửi cả thông báo lẫn email
                guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
                guiEmailDonHang(donHang, thongTinGiaoHang);
            }

            return "redirect:/quan-ly/don-hang/da-duyet";

        } else if (donHang.getTrangThai() == 1) {
            donHang.setTrangThai(2);
            this.iDonHangRepo.save(donHang);
            ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
            KhachHang khachHangDangNhap = donHang.getKhachHang();
            String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
            if ("0".equals(loaiKhachHang)) {
                // Loại khách hàng = 0, chỉ gửi email
                guiEmailDonHang(donHang, thongTinGiaoHang);
            } else {
                // Loại khách hàng = 1, gửi cả thông báo lẫn email
                guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
                guiEmailDonHang(donHang, thongTinGiaoHang);
            }
            return "redirect:/quan-ly/don-hang/dang-giao";


        } else if (donHang.getTrangThai() == 2) {
            donHang.setTrangThai(3);
            this.iDonHangRepo.save(donHang);
            ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
            KhachHang khachHangDangNhap = donHang.getKhachHang();
            String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
            if ("1".equals(loaiKhachHang)) {
                for (DonHangChiTiet dhct : donHang.getChiTietDonHang()) {
                    iKiemTraDanhGiaService.save(donHang.getKhachHang(), dhct.getSach());
                }
            }
            if ("0".equals(loaiKhachHang)) {
                // Loại khách hàng = 0, chỉ gửi email
                guiEmailDonHang(donHang, thongTinGiaoHang);
            } else {
                // Loại khách hàng = 1, gửi cả thông báo lẫn email
                guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
                guiEmailDonHang(donHang, thongTinGiaoHang);
            }
            return "redirect:/quan-ly/don-hang/hoan-thanh";

        }
        return "redirect:/quan-ly/don-hang/hoan-thanh";

    }

    @GetMapping("/xac-nhan-don-hang-cho")
    public String xacNhanDonHangCho(Model model, HttpSession session,
                                    @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }


        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 1) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setNhanVien(nhanVien);
            donHang.setTrangThai(1); // Đặt trạng thái thành 1 (đã duyệt)
            iDonHangRepo.save(donHang);

            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }
        ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
        KhachHang khachHangDangNhap = donHang.getKhachHang();

// Kiểm tra loại khách hàng
        String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
        if ("0".equals(loaiKhachHang)) {
            // Loại khách hàng = 0, chỉ gửi email
            guiEmailDonHang(donHang, thongTinGiaoHang);
        } else {
            // Loại khách hàng = 1, gửi cả thông báo lẫn email
            guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
            guiEmailDonHang(donHang, thongTinGiaoHang);
        }

        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/cho-xac-nhan";

    }

    @GetMapping("/xac-nhan-huy-don/{id}")
    public String xacNhanDonHangHuy(Model model, HttpSession session,
                                    @PathVariable("id") int idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(idDonHang);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }
        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 4) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và thời gian hủy
            donHang.setTrangThai(4); // Đặt trạng thái thành 3 (đã hủy)

            // Lưu thời gian hủy
            Date thoiGianHuy = new Date();
            donHang.setNgayHuy(thoiGianHuy);

            iDonHangRepo.save(donHang);
            // Tăng số lượng tồn kho cho từng sản phẩm trong đơn hàng
            for (DonHangChiTiet dhct : donHang.getChiTietDonHang()) {
                donHangService.tangSoLuongTonKho(dhct);
            }
            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }

        ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
        KhachHang khachHangDangNhap = donHang.getKhachHang();

// Kiểm tra loại khách hàng
        String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
        if ("0".equals(loaiKhachHang)) {
            // Loại khách hàng = 0, chỉ gửi email
            guiEmailDonHang(donHang, thongTinGiaoHang);
        } else {
            // Loại khách hàng = 1, gửi cả thông báo lẫn email
            guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
            guiEmailDonHang(donHang, thongTinGiaoHang);
        }

        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/da-huy";
    }
    //KẾT THÚC CỦA ĐƠN HÀNG CHỜ


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐÃ DUYỆT
    // bắt đầu hiển thị giao diện đơn hàng đã duyệt
    @GetMapping("/don-hang/da-duyet")
    public String quanLyDonHangDaDuyet(HttpSession session, Model model,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(required = false) String maDonHang,
                                       @RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate) {
        Page<DonHang> pageDonHang;
        int pageSize = 5;
        model.addAttribute("std", "");
        model.addAttribute("end", "");
        model.addAttribute("ma", "");
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if (maDonHang != null) {
            // xử lý trạng thái
            model.addAttribute("std", "");
            model.addAttribute("end", "");
            model.addAttribute("ma", "");
            Date ngayBatDau = null;
            Date ngayKetThuc = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (startDate != null) {
                    ngayBatDau = sdf.parse(String.valueOf(startDate));
                    model.addAttribute("std", startDate);

                }

                if (endDate != null) {
                    ngayKetThuc = sdf.parse(String.valueOf(endDate));
                    model.addAttribute("end", endDate);

                }
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the exception properly in a real-world scenario
            }
            pageDonHang = donHangService.searchDOnHang(maDonHang, ngayBatDau, ngayKetThuc, 1, pageable);
        } else {
            pageDonHang = iDonHangRepo.findAllByTrangThaiOrderByIdDonHangDesc(pageable, 1);
        }
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", pageDonHang);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaDuyet";
    }

    // kết thúc hiển thị giao diện đơn hàng đã duyệt
//
    @GetMapping("/xac-nhan-don-hang-da-duyet")
    public String xacNhanDonHangDaGiao(Model model, HttpSession session,
                                       @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
        KhachHang khachHangDangNhap = donHang.getKhachHang();


// Kiểm tra loại khách hàng
        String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
        if (nhanVien == null) {
            return "redirect:/login";
        }

        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 2) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setTrangThai(2); // Đặt trạng thái thành 1 (đã duyệt)
            donHang.setNgayThanhToan(new Date()); // Cập nhật ngày tạo mới
            if ("1".equals(loaiKhachHang)) {
                for (DonHangChiTiet dhct : donHang.getChiTietDonHang()) {
                    iKiemTraDanhGiaService.save(donHang.getKhachHang(), dhct.getSach());
                }
            }
            iDonHangRepo.save(donHang);

            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }


        if ("0".equals(loaiKhachHang)) {
            // Loại khách hàng = 0, chỉ gửi email
            guiEmailDonHang(donHang, thongTinGiaoHang);
        } else {
            // Loại khách hàng = 1, gửi cả thông báo lẫn email
            guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
            guiEmailDonHang(donHang, thongTinGiaoHang);
        }


        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/da-duyet";

    }
    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ DUYỆT


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐANG GIAO
    @GetMapping("/don-hang/dang-giao")
    // bắt đầu hiển thị giao diện đơn hàng đang giao
    public String quanLyDonHangDangGiao(HttpSession session, Model model,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(required = false) String maDonHang,
                                        @RequestParam(required = false) String startDate,
                                        @RequestParam(required = false) String endDate) {

        Page<DonHang> pageDonHang;
        int pageSize = 5;
        model.addAttribute("std", "");
        model.addAttribute("end", "");
        model.addAttribute("ma", "");
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if (maDonHang != null) {
            // xử lý trạng thái

            Date ngayBatDau = null;
            Date ngayKetThuc = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (startDate != null) {
                    ngayBatDau = sdf.parse(String.valueOf(startDate));
                    model.addAttribute("std", startDate);

                }

                if (endDate != null) {
                    ngayKetThuc = sdf.parse(String.valueOf(endDate));
                    model.addAttribute("end", endDate);

                }
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the exception properly in a real-world scenario
            }
            pageDonHang = donHangService.searchDOnHang(maDonHang, ngayBatDau, ngayKetThuc, 2, pageable);
        } else {
            pageDonHang = iDonHangRepo.findAllByTrangThaiOrderByIdDonHangDesc(pageable, 2);
        }
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }


        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", pageDonHang);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDangGiao";
    }
    // kết thúc hiển thị giao diện đơn hàng đang giao

    @GetMapping("/xac-nhan-don-hang-dang-giao")
    public String xacNhanDonHangDaDuyet(Model model, HttpSession session,
                                        @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
        KhachHang khachHangDangNhap = donHang.getKhachHang();

// Kiểm tra loại khách hàng
        String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
        if (nhanVien == null) {
            return "redirect:/login";
        }

        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 3) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setTrangThai(3); // Đặt trạng thái thành 1 (đã duyệt)
            donHang.setNgayThanhToan(new Date()); // Cập nhật ngày tạo mới
            if ("1".equals(loaiKhachHang)) {
                for (DonHangChiTiet dhct : donHang.getChiTietDonHang()) {
                    iKiemTraDanhGiaService.save(donHang.getKhachHang(), dhct.getSach());
                }
            }
            iDonHangRepo.save(donHang);

            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }

        if ("0".equals(loaiKhachHang)) {
            // Loại khách hàng = 0, chỉ gửi email
            guiEmailDonHang(donHang, thongTinGiaoHang);
        } else {
            // Loại khách hàng = 1, gửi cả thông báo lẫn email
            guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
            guiEmailDonHang(donHang, thongTinGiaoHang);
        }

        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/dang-giao";

    }
    //KẾT THÚC CỦA ĐƠN HÀNG ĐANG GIAO


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐÃ HOÀN THÀNH
    @GetMapping("/don-hang/hoan-thanh")
    // bắt đầu hiển thị giao diện đơn hàng đã hoàn thành
    public String quanLyDangGiaoHang(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(required = false) String maDonHang,
                                     @RequestParam(required = false) String startDate,
                                     @RequestParam(required = false) String endDate) {
        Page<DonHang> pageDonHang;
        int pageSize = 5;
        model.addAttribute("std", "");
        model.addAttribute("end", "");
        model.addAttribute("ma", "");
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if (maDonHang != null) {
            // xử lý trạng thái
            Date ngayBatDau = null;
            Date ngayKetThuc = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (startDate != null) {
                    ngayBatDau = sdf.parse(String.valueOf(startDate));
                    model.addAttribute("std", startDate);
                }
                if (endDate != null) {
                    ngayKetThuc = sdf.parse(String.valueOf(endDate));
                    model.addAttribute("end", endDate);
                }
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the exception properly in a real-world scenario
            }
            pageDonHang = donHangService.searchDOnHang(maDonHang, ngayBatDau, ngayKetThuc, 3, pageable);
        } else {
            pageDonHang = iDonHangRepo.findAllByTrangThaiOrderByIdDonHangDesc(pageable, 3);
        }
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("donHang", pageDonHang);
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaHoanThanh";
    }
    // bắt đầu hiển thị giao diện đơn hàng đã hoàn thành

    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ HOÀN THÀNH


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐÃ HỦY
    @GetMapping("/don-hang/da-huy")
    // bắt đầu hiển thị giao diện đơn hàng đã hủy
    public String quanLyDonHangDaHuy(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(required = false) String maDonHang,
                                     @RequestParam(required = false) String startDate,
                                     @RequestParam(required = false) String endDate) {
        Page<DonHang> pageDonHang;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        model.addAttribute("std", "");
        model.addAttribute("end", "");
        model.addAttribute("ma", "");
        if (maDonHang != null) {
            // xử lý trạng thái
            model.addAttribute("std", "");
            model.addAttribute("end", "");
            model.addAttribute("ma", "");
            Date ngayBatDau = null;
            Date ngayKetThuc = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (startDate != null) {
                    ngayBatDau = sdf.parse(String.valueOf(startDate));
                    model.addAttribute("std", startDate);

                }

                if (endDate != null) {
                    ngayKetThuc = sdf.parse(String.valueOf(endDate));
                    model.addAttribute("end", endDate);

                }
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the exception properly in a real-world scenario
            }
            pageDonHang = donHangService.searchDOnHang(maDonHang, ngayBatDau, ngayKetThuc, 4, pageable);
        } else {
            pageDonHang = iDonHangRepo.findAllByTrangThaiOrderByIdDonHangDesc(pageable, 4);
        }
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", pageDonHang);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaHuy";
    }
    // bắt đầu hiển thị giao diện đơn hàng đã hủy

    @GetMapping("/xac-nhan-giao-lai")
    public String giaoLaiDonHang(@RequestParam("idDonHang") String idDonHang, Model model, HttpSession session) {
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");


        if (donHang != null) {
            // Kiểm tra trạng thái của đơn hàng
            if (donHang.getTrangThai() == 4) {
                // Nếu trạng thái là 3 (hủy), thì thực hiện giao lại
                donHang.setTrangThai(0); // Đặt lại trạng thái của đơn hàng thành chờ (status = 0)
                donHang.setNgayTao(new Date()); // Cập nhật ngày tạo mới
                DonHang donHang1 = iDonHangRepo.save(donHang);

                for (DonHangChiTiet dhct : donHang1.getChiTietDonHang()) {
                    donHangService.truSoLuongTonKho(dhct);
                }
            }

            ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
            KhachHang khachHangDangNhap = donHang.getKhachHang();

// Kiểm tra loại khách hàng
            String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
            if ("0".equals(loaiKhachHang)) {
                // Loại khách hàng = 0, chỉ gửi email
                guiEmailDonHang(donHang, thongTinGiaoHang);
            } else {
                // Loại khách hàng = 1, gửi cả thông báo lẫn email
                guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
                guiEmailDonHang(donHang, thongTinGiaoHang);
            }

            // In thông tin để kiểm tra
            model.addAttribute("loggedInUser", nhanVien);
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }

        return "redirect:/quan-ly/don-hang/cho-xac-nhan";

    }

    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ HỦY

    public void guiEmailDonHang(DonHang donHang, ThongTinGiaoHang thongTinGiaoHang) {
        String trangThaiDonHang = "";
        switch (donHang.getTrangThai()) {
            // ... (your existing code for switch cases)

            default:
                trangThaiDonHang = "Trạng thái đơn hàng không xác định";
        }

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Mã Đơn Hàng của bạn: ").append(donHang.getMaDonHang()).append("\n")
                .append("Địa chỉ nhận hàng: ").append(donHang.getThongTinGiaoHang().getDiaChiChu()).append("\n")
                .append("Trạng thái đơn: ").append(donHang.getTrangThai()).append("\n")
                .append("Danh sách sản phẩm:\n");

        // Iterate through DonHangChiTiet items and include product details
        for (DonHangChiTiet donHangChiTiet : donHang.getChiTietDonHang()) {
            emailContent.append(" - Sản phẩm: ").append(donHangChiTiet.getSach().getTenSach()).append("\n")
                    .append("   Số lượng: ").append(donHangChiTiet.getSoLuong()).append("\n")
                    .append("   Đơn giá thời điểm mua: ").append(donHangChiTiet.donGiaThoidiemMuaVnd()).append("\n")
                    .append("   Thành tiền: ").append(donHangChiTiet.thanhTienVnd()).append("\n");
        }

        String subject = "Dưới đây là mã đơn hàng và trạng thái đơn hàng của bạn!! ";
        senderService.sendSimpleEmail(thongTinGiaoHang.getEmailGiaoHang(), subject, emailContent.toString());
    }

    public void guiThongBaoDonHang(DonHang donHang, ThongTinGiaoHang thongTinGiaoHang, KhachHang khachHang) {
        Date currDate = new Date();
        String trangThaiMessage = "";

        switch (donHang.getTrangThai()) {
            case 1:
                trangThaiMessage = "Đơn hàng của bạn đã được xác nhận !!!";
                break;
            case 2:
                trangThaiMessage = "Đơn hàng của bạn đang được vận chuyển !!!!";
                break;
            case 3:
                trangThaiMessage = "Đơn hàng của bạn đã được hoàn thành !!!!";
                break;
            case 4:
                if (donHang.getTrangThai() == 0) {
                    trangThaiMessage = "Đơn hàng của bạn đang được giao lại !!!";
                } else {
                    trangThaiMessage = "Đơn hàng của bạn đã bị hủy vui lòng đặt lại hàng!!!";
                }
                break;
            default:
                trangThaiMessage = "Trạng thái đơn hàng không xác định";
        }

        String noiDung = "Thông Báo về đơn hàng " + donHang.getMaDonHang() + "\n" + trangThaiMessage + "\n"
                + "Cảm ơn đã mua hàng!";

        ThongBao thongBao = new ThongBao();
        thongBao.setNgayGui(currDate);
        thongBao.setNoiDung(noiDung);

        // lưu thông báo vào db
        ThongBao savedNotification = this.iThongBaoService.createNew(thongBao);

        // gọi hàm
        this.thongBaoKhachHangService.themThongBaoChoNguoiDung(khachHang.getIdKhachHang(), savedNotification);
    }

    @GetMapping("/don-hang/export/pdf")
    public String exportToPDF(RedirectAttributes redirectAttributes, HttpServletResponse response, @RequestParam("idDonHang") String idDonHang, Model model, HttpSession session)
            throws DocumentException, IOException {
        // Cài đặt các header và loại nội dung cho response
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Lấy đơn hàng từ cơ sở dữ liệu
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));

        // Kiểm tra xem đơn hàng có tồn tại và trạng thái không phải là 'Xác nhận' (1) hay không
        if (donHang != null && donHang.getTrangThai() == 0) {
            // Cập nhật trạng thái của đơn hàng thành 'Xác nhận' (1)
            donHang.setNhanVien(nhanVien);
            donHang.setTrangThai(1);
            iDonHangRepo.save(donHang);

            // Xuất hóa đơn PDF
            PDFExporter exporter = new PDFExporter();
            exporter.export(response, donHang);
            ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
            KhachHang khachHangDangNhap = donHang.getKhachHang();

// Kiểm tra loại khách hàng
            String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
            if ("0".equals(loaiKhachHang)) {
                // Loại khách hàng = 0, chỉ gửi email
                guiEmailDonHang(donHang, thongTinGiaoHang);
            } else {
                // Loại khách hàng = 1, gửi cả thông báo lẫn email
                guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
                guiEmailDonHang(donHang, thongTinGiaoHang);
            }

            // In thông tin để kiểm tra
            model.addAttribute("loggedInUser", nhanVien);
            // Tiếp tục với các lệnh sau khi xuất PDF (ví dụ: gửi thông báo chuyển hướng)
            redirectAttributes.addFlashAttribute("successMessage", "Đã xác nhận đơn hàng thành công.");
        } else {
            // Trả về thông báo lỗi nếu không tìm thấy đơn hàng hoặc đơn hàng đã ở trạng thái 'Xác nhận'
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể tạo hóa đơn cho Đơn hàng có idDonHang " + idDonHang +
                    ". Đơn hàng không được tìm thấy hoặc đã ở trạng thái 'Xác nhận'.");
        }

        // Chuyển hướng về trang sau khi xử lý thành công hoặc thất bại
        return "/admin/quanly/DonHangCho";
    }


}
