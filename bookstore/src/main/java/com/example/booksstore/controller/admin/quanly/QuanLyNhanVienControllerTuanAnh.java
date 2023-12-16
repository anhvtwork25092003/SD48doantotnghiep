package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.service.INhanVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/quan-ly")
@Validated
public class QuanLyNhanVienControllerTuanAnh {

    private static boolean hasNonNumericCharacters(String phoneNumber) {
        // Define a regular expression pattern for finding non-numeric characters
        String regex = "[^0-9]";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Check if the phone number contains any non-numeric characters
        return pattern.matcher(phoneNumber).find();
    }

    public static boolean isEmailValid(String email) {
        // Regular expression to check if email contains '@'
        String regex = ".*@.*";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create matcher object
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the pattern
        return matcher.matches();
    }

    @Autowired
    INhanVienService service;
    @Value("${upload.anhnhanvien}")
    private String uploadanhnhanvien;

    @GetMapping("/nhan-vien/hien-thi")
    public String hienThiManHinhQuanlyNhanVien(Model model, @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(required = false) String memberNameSearch,
                                               @RequestParam(required = false) String memberCodeSearch,
                                               @RequestParam(required = false) String memberStatusSearch,
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
        Page<NhanVien> pageOfNhanVien;
        int trangThai = 0;
        int pageSize = 2; // Đặt kích thước trang mặc định
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0
//  moi khoi tao trang
        if (memberNameSearch != null || memberCodeSearch != null || memberStatusSearch != null) {

            // xuu ly trang thai
            if (memberStatusSearch.equals("99")) {
                trangThai = -1;
            } else if (memberStatusSearch.equals("1")) {
                trangThai = 1;
            } else if (memberStatusSearch.equals("0")) {
                trangThai = 0;
            }
            pageOfNhanVien = service.searchNhanVien(memberNameSearch, memberCodeSearch, trangThai, pageable);
        } else {
            pageOfNhanVien = service.pageOfNhanVien(pageable);
        }

        model.addAttribute("pageOfNhanVien", pageOfNhanVien);
        return "/user/nhanvien/thanh_nhanvien";
    }

    @Transactional
    @PostMapping("/nhan-vien/them-moi")
    public String themNhanVien(

            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("ngaySinh") Date ngaySinh,
            @RequestParam("trangThai") String trangThai,
            @RequestParam("matKhau") String matKhau,
            @RequestParam("email") String email,
            @RequestParam("chucVu") String chucVu,
            @RequestParam("linkAnhNhanVien") MultipartFile linkAnhNhanVien,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            List<String> listLoiValidate = new ArrayList<>();
            int countLoiValidate = 0;
            if (hoVaTen.trim().length() > 50     ) {
                listLoiValidate.add("Tên ngắn dưới 50 kí tự và không để trống , chữ cái đầu phải viết hoa!");
                countLoiValidate = countLoiValidate + 1;
            }
            if ( hoVaTen.trim().length() == 0){
                listLoiValidate.add(" Tên không được để trống");
                countLoiValidate = countLoiValidate + 1;
            }
            if (sdt.trim().length() > 20 ) {
                listLoiValidate.add("Số điện thoại không quá 20 và không để trống , không được chứa kí tự !");
                countLoiValidate = countLoiValidate + 1;
            }
            if (sdt.trim().length() == 0 ){
                listLoiValidate.add("Số điện thoại không để trống  !");
                countLoiValidate = countLoiValidate + 1;
            }
            if(hasNonNumericCharacters(sdt)){
                listLoiValidate.add("Số điện thoại  không được chứa kí tự !");
                countLoiValidate = countLoiValidate + 1;
            }

            if (matKhau.trim().length() < 0) {
                listLoiValidate.add("mật khẩu phải lớn hơn 0");
                countLoiValidate = countLoiValidate + 1;
            }
            if (email.trim().length() <0) {
                listLoiValidate.add("Email phải dài hơn 0");
                countLoiValidate = countLoiValidate + 1;
            }
            if(isEmailValid(email)){
                listLoiValidate.add("Email phải có @");
                countLoiValidate = countLoiValidate + 1;
            }
            if (countLoiValidate > 0) {
                redirectAttributes.addFlashAttribute("danhSachLoiValidate", listLoiValidate);
                return "redirect:/quan-ly/nhan-vien/hien-thi";
            }

            String duongDanCotDinh = "/image/anhnhanvien/";
            String duongDanLuuAnh = duongDanCotDinh + linkAnhNhanVien.getOriginalFilename();
            System.out.println(duongDanCotDinh + linkAnhNhanVien.getOriginalFilename());
            if (linkAnhNhanVien.isEmpty()) {
                duongDanLuuAnh = "";
            } else {
                byte[] bytes = linkAnhNhanVien.getBytes();
                Path path = Paths.get(uploadanhnhanvien + linkAnhNhanVien.getOriginalFilename());
                Files.write(path, bytes);
            }
            NhanVien nhanvien = NhanVien.builder()
                    .hoVaTen(hoVaTen)
                    .sdt(sdt)
                    .ngaySinh(ngaySinh)
                    .trangThai(Integer.parseInt(trangThai))
                    .matKhau(matKhau)
                    .email(email)
                    .chucVu(chucVu)
                    .linkAnhNhanVien(duongDanLuuAnh)
                    .build();

            this.service.save(nhanvien);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/quan-ly/nhan-vien/hien-thi";
    }

    @PostMapping("/nhan-vien/cap-nhat")
    public String nhanviensua(
            @RequestParam("checkthayDoiImage") String trangThaiThayDoiAnh1,
            @RequestParam("idNhanVien") String idNhanVien,
            @RequestParam("maNhanVien") String maNhanVien,
            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("ngaySinh") Date ngaySinh,
            @RequestParam("trangThai") String trangThai,
            @RequestParam("matKhau") String matKhau,
            @RequestParam("email") String email,
            @RequestParam("chucVu") String chucVu,
            @RequestParam("editlinkAnhNhanVien") MultipartFile linkAnhNhanVien

    ) throws IOException {
        System.out.println(trangThaiThayDoiAnh1);
        NhanVien nhanVien = this.service.getOne(Integer.parseInt(idNhanVien));
        String duongDanCotDinh = "/image/anhnhanvien/";
        String duongDanLuuAnhNhanVien = "";
        if (trangThaiThayDoiAnh1.equalsIgnoreCase("DaThayDoi")) {
            if (linkAnhNhanVien.isEmpty()) {
                duongDanLuuAnhNhanVien = "";
            } else {
                byte[] bytes = linkAnhNhanVien.getBytes();
                Path path = Paths.get(uploadanhnhanvien + linkAnhNhanVien.getOriginalFilename());
                Files.write(path, bytes);
                duongDanLuuAnhNhanVien = duongDanCotDinh + linkAnhNhanVien.getOriginalFilename();
            }
        } else {
            duongDanLuuAnhNhanVien = nhanVien.getLinkAnhNhanVien();
        }
        NhanVien nhanvienUpDate = NhanVien.builder()
                .idNhanVien(Integer.parseInt(idNhanVien))
                .maNhanVien(maNhanVien)
                .hoVaTen(hoVaTen)
                .sdt(sdt)
                .ngaySinh(ngaySinh)
                .trangThai(Integer.parseInt(trangThai))
                .matKhau(matKhau)
                .email(email)
                .chucVu(chucVu)
                .linkAnhNhanVien(duongDanLuuAnhNhanVien)
                .build();
        this.service.save(nhanvienUpDate);
        return "redirect:/quan-ly/nhan-vien/hien-thi";
    }

}
