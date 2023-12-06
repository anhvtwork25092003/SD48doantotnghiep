package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.*;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.repository.NhanVienRepository;
import com.example.booksstore.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/quan-ly")
public class DonHangControllerTuanAnh {

    @Autowired
    IDonHangRepo iDonHangRepo;

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
    public String quanLyDonHangCho(@RequestParam(defaultValue = "1") int page,
                                   Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable, 0);

        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", donHangs);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangCho";
    }

    @PostMapping("/tim-kiem-ma")
    public String searchOrders(@RequestParam("maDonHang") String maDonHang,
                               @RequestParam("sdt") String sdt, HttpSession session,
                               Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> timKiemMaAndSdt = iDonHangRepo.findByMaDonHangContainingAndKhachHang_SdtContaining(pageable, maDonHang, sdt);
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", timKiemMaAndSdt);
        System.out.println("Aaaaaaaaaaa" + timKiemMaAndSdt);
        return "/admin/quanly/DonHangCho";
    }

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
        } else   {
            // Loại khách hàng = 1, gửi cả thông báo lẫn email
            guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
            guiEmailDonHang(donHang, thongTinGiaoHang);
        }

        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/da-duyet";

    }

    @GetMapping("/xac-nhan-huy-don")
    public String xacNhanDonHangHuy(Model model, HttpSession session,
                                    @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }

        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 3) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và thời gian hủy
            donHang.setTrangThai(3); // Đặt trạng thái thành 3 (đã hủy)

            // Lưu thời gian hủy
            Date thoiGianHuy = new Date();
            donHang.setNgayHuy(thoiGianHuy);

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
        } else   {
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
    @GetMapping("/don-hang/da-duyet")
    public String quanLyDonHangDaDuyet(HttpSession session, Model model,
                                       @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable, 1);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", donHangs);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaDuyet";
    }

    @GetMapping("/xac-nhan-don-hang-da-giao")
    public String xacNhanDonHangDaGiao(Model model, HttpSession session,
                                       @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        }

        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 2) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setTrangThai(2); // Đặt trạng thái thành 1 (đã duyệt)
            donHang.setNgayThanhToan(new Date()); // Cập nhật ngày tạo mới
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
        } else   {
            // Loại khách hàng = 1, gửi cả thông báo lẫn email
            guiThongBaoDonHang(donHang, thongTinGiaoHang, khachHangDangNhap);
            guiEmailDonHang(donHang, thongTinGiaoHang);

            for(DonHangChiTiet dhct : donHang.getChiTietDonHang()){
                iKiemTraDanhGiaService.save(donHang.getKhachHang(),dhct.getSach());
            }
        }

        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/hoan-thanh";

    }
    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ DUYỆT


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐÃ HOÀN THÀNH
    @GetMapping("/don-hang/hoan-thanh")
    public String quanLyDangGiaoHang(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable, 2);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("donHang", donHangs);
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaHoanThanh";
    }
    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ HOÀN THÀNH


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐÃ HỦY
    @GetMapping("/don-hang/da-huy")
    public String quanLyDonHangDaHuy(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable, 3);
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", donHangs);
        model.addAttribute("nhanviens", iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaHuy";
    }

    @GetMapping("/xac-nhan-giao-lai")
    public String giaoLaiDonHang(@RequestParam("idDonHang") String idDonHang, Model model, HttpSession session) {
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");



        if (donHang != null) {
            // Kiểm tra trạng thái của đơn hàng
            if (donHang.getTrangThai() == 3) {
                // Nếu trạng thái là 3 (hủy), thì thực hiện giao lại
                donHang.setTrangThai(0); // Đặt lại trạng thái của đơn hàng thành chờ (status = 0)
                donHang.setNgayTao(new Date()); // Cập nhật ngày tạo mới


                iDonHangRepo.save(donHang);
            }

            ThongTinGiaoHang thongTinGiaoHang = donHang.getThongTinGiaoHang();
            KhachHang khachHangDangNhap = donHang.getKhachHang();

// Kiểm tra loại khách hàng
            String loaiKhachHang = khachHangDangNhap.getLoaiKhachHang();
            if ("0".equals(loaiKhachHang)) {
                // Loại khách hàng = 0, chỉ gửi email
                guiEmailDonHang(donHang, thongTinGiaoHang);
            } else   {
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


//    public void guiEmailDonHang(DonHang donHang,ThongTinGiaoHang thongTinGiaoHang) {
//        String subject = "Dưới đây là mã đơn hàng và trạng thái đơn hàng của bạn!! "  ;
//        senderService.sendSimpleEmail(thongTinGiaoHang.getEmailGiaoHang(), subject,
//                "Mã Đơn Hàng của bạn " + donHang.getMaDonHang() + "\n" +
//                        "Trạng thái đơn: " + donHang.getTrangThai());
//
//    }

    public void guiEmailDonHang(DonHang donHang, ThongTinGiaoHang thongTinGiaoHang) {
        String trangThaiDonHang = "";
        switch (donHang.getTrangThai()) {
            case 1:
                trangThaiDonHang = "Đơn hàng của bạn đã được xác nhận và đang được vận chuyển!!!";
                break;
            case 2:
                trangThaiDonHang = "Đơn hàng của bạn đã được hoàn thành!!!!";
                break;
            case 3:
                if (donHang.getTrangThai() == 0) {
                    trangThaiDonHang = "Đơn hàng của bạn đang được giao lại !!!";
                } else {
                    trangThaiDonHang = "Đơn hàng của bạn đã bị hủy vui lòng đặt lại hàng!!!";
                }
                break;
            default:
                trangThaiDonHang = "Trạng thái đơn hàng không xác định";
        }

        String subject = "Dưới đây là mã đơn hàng và trạng thái đơn hàng của bạn!! ";
        senderService.sendSimpleEmail(thongTinGiaoHang.getEmailGiaoHang(), subject,
                "Mã Đơn Hàng của bạn " + donHang.getMaDonHang() + "\n" +
                        "Trạng thái đơn: " + trangThaiDonHang);
    }



    public void guiThongBaoDonHang(DonHang donHang, ThongTinGiaoHang thongTinGiaoHang, KhachHang khachHang) {
        Date currDate = new Date();
        String trangThaiMessage = "";

        switch (donHang.getTrangThai()) {
            case 1:
                trangThaiMessage = "Đơn hàng của bạn đã được xác nhận và đang được vận chuyển!!!";
                break;
            case 2:
                trangThaiMessage = "Đơn hàng của bạn đã được hoàn thành!!!!";
                break;
            case 3:
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

}
