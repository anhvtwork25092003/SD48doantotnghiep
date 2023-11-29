package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.repository.DonHangChiTietRepo;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.NhanVienRepository;
import com.example.booksstore.service.INhanVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //BẮT ĐẦU CỦA ĐƠN HÀNG CHỜ
    @GetMapping("/don-hang/cho-xac-nhan")
    public String quanLyDonHangCho(@RequestParam(defaultValue = "1") int page,
                                   Model model, HttpSession session) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable,0);
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang",donHangs);
        model.addAttribute("nhanviens",iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangCho";
    }

    @PostMapping("/tim-kiem-ma")
    public String searchOrders(@RequestParam("maDonHang") String maDonHang,
                               @RequestParam("sdt") String sdt,HttpSession session,
                               Model model,@RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> timKiemMaAndSdt = iDonHangRepo.findByMaDonHangContainingAndKhachHang_SdtContaining(pageable,maDonHang,sdt);
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang", timKiemMaAndSdt);
        System.out.println("Aaaaaaaaaaa"+ timKiemMaAndSdt);
        return "/admin/quanly/DonHangCho";
    }

//    @PostMapping("/cap-nhat-nv")
//    public String capNhatNhanVien(Model model,
//                                  @RequestParam("idNhanVien") String idNhanVien,
//                                  @RequestParam("idDonHang") String idDonHang) {
//        try {
//            // Lấy đơn hàng từ idDonHang
//            int donHangId = Integer.parseInt(idDonHang);
//            DonHang donHang = iDonHangRepo.findByIdDonHang(donHangId);
//
//            // Lấy thông tin nhân viên từ idNhanVien
//            int nhanVienId = Integer.parseInt(idNhanVien);
//            NhanVien nhanVien = iNhanVienService.getOne(nhanVienId);
//
//            // Thêm nhân viên vào đơn hàng
//            donHang.setNhanVien(nhanVien);
//
//            // Lưu đơn hàng đã được cập nhật
//            iDonHangRepo.save(donHang);
//
//            // Thêm thông báo thành công vào model (nếu cần)
//            model.addAttribute("successMessage", "Nhân viên đã được thêm vào đơn hàng.");
//
//        } catch (NumberFormatException e) {
//            // Xử lý lỗi chuyển đổi số nguyên
//            model.addAttribute("errorMessage", "Lỗi: Định dạng số nguyên không hợp lệ.");
//        } catch (Exception e) {
//            // Xử lý lỗi khác nếu có
//            model.addAttribute("errorMessage", "Lỗi: " + e.getMessage());
//        }
//
//        // Điều hướng về trang quản lý đơn hàng chờ xác nhận
//        return "redirect:/quan-ly/don-hang/cho-xac-nhan";
//
//    }

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
    public String xacNhanDonHangCho(Model model,HttpSession session,
                                 @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 1) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setTrangThai(1); // Đặt trạng thái thành 1 (đã duyệt)
            iDonHangRepo.save(donHang);

            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }
        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/da-duyet";

    }

    @GetMapping("/xac-nhan-huy-don")
    public String xacNhanDonHangHuy(Model model,HttpSession session,
                                    @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 3) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setTrangThai(3); // Đặt trạng thái thành 1 (đã duyệt)
            iDonHangRepo.save(donHang);

            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
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
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable,1);
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang",donHangs);
        model.addAttribute("nhanviens",iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaDuyet";
    }

    @GetMapping("/xac-nhan-don-hang-da-giao")
    public String xacNhanDonHangDaGiao(Model model,HttpSession session,
                                    @RequestParam("idDonHang") String idDonHang) {
        // Lấy đơn hàng từ idDonHang
        DonHang donHang = iDonHangRepo.findByIdDonHang(Integer.parseInt(idDonHang));
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        // Kiểm tra xem đơn hàng đã được duyệt chưa
        if (donHang.getTrangThai() != 2) {
            // Nếu đơn hàng chưa được duyệt, thì cập nhật trạng thái và lưu lại
            donHang.setTrangThai(2); // Đặt trạng thái thành 1 (đã duyệt)
            iDonHangRepo.save(donHang);

            // In thông tin để kiểm tra
            System.out.println("Đã xác nhận và cập nhật trạng thái đơn hàng: " + donHang);
        }
        model.addAttribute("loggedInUser", nhanVien);
        // Chuyển hướng về trang đơn đã duyệt
        return "redirect:/quan-ly/don-hang/hoan-thanh";

    }
    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ DUYỆT


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐÃ HOÀN THÀNH
    @GetMapping("/don-hang/hoan-thanh")
    public String quanLyDangGiaoHang(Model model, HttpSession session,@RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable,2);
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        model.addAttribute("donHang",donHangs);
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("nhanviens",iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaHoanThanh";
    }
    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ HOÀN THÀNH


    //BẮT ĐẦU CỦA ĐƠN HÀNG ĐÃ HỦY
    @GetMapping("/don-hang/da-huy")
    public String quanLyDonHangDaHuy(Model model, HttpSession session,@RequestParam(defaultValue = "1") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DonHang> donHangs = iDonHangRepo.findAllByTrangThaiOrderByIdDonHang(pageable,3);
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", nhanVien);
        model.addAttribute("donHang",donHangs);
        model.addAttribute("nhanviens",iNhanVienService.pageOfNhanVien(pageable));
        return "/admin/quanly/DonHangDaHuy";
    }


    //KẾT THÚC CỦA ĐƠN HÀNG ĐÃ HỦY

}
