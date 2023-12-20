package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.repository.TacGiaRepository;
import com.example.booksstore.repository.TheLoaiRepository;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping("/quan-ly")
public class QuanLySachController {


    @Autowired
    ISachService iSachService;
    @Autowired
    TacGiaRepository tacGiaRepository;

    @Autowired
    TacGiaService tacGiaService;

    @Autowired
    ITheLoaiServiec iTheLoaiService;


    @Autowired
    TheLoaiRepository theLoaiRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;

    private String formatCurrency(BigDecimal amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(amount);
    }

    @GetMapping("/sach/hien-thi")
    public String hienThiTrangTongQuanQuanLy(Model model, @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(required = false) String productNameSearch,
                                             @RequestParam(required = false) String productCodeSearch,
                                             @RequestParam(value = "productStatusSearch", required = false) String productStatusSearch,
                                             HttpSession session
    ) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("loggedInUser", nhanVien);

        }
        Page<Sach> pageOfSach;
        BigDecimal giaMin = null;
        BigDecimal giaMax = null;
        int trangThai = -1;
        int pageSize = 5; // Đặt kích thước trang mặc định
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0
        //  moi khoi tao trang
        // xu ly khoang gia

        if (productCodeSearch == null) {
            model.addAttribute("pc", "");
        } else {
            model.addAttribute("pc", productCodeSearch);
        }
        if (productNameSearch != null) {
            model.addAttribute("pn", productNameSearch);
        } else {
            model.addAttribute("pn", "");
        }

        // xuu ly trang thai
        if (productStatusSearch != null) {
            if (productStatusSearch.equals("S1")) {
                trangThai = -1;
            } else if (productStatusSearch.equals("S2")) {
                trangThai = 1;
            } else if (productStatusSearch.equals("S3")) {
                trangThai = 0;
            }
            // gửi modal để phân trang link
            model.addAttribute("psta", productStatusSearch);

        }
        pageOfSach = iSachService.searchSach(productNameSearch, productCodeSearch, trangThai, pageable);

        // Replace the original list of Sach objects with the formatted list

        // Add the formatted Page<Sach> object to the model
        model.addAttribute("pageOfSach", pageOfSach);

        model.addAttribute("pageOfSach", pageOfSach);
        model.addAttribute("authors", tacGiaRepository.findAllByTrangThai(1));
        model.addAttribute("listTheLoai", theLoaiRepository.findAllByTrangThai(1));
        return "user/sanpham/sanpham";
    }


    @Transactional
    @PostMapping("/sach/them-moi")
    public String themMoiSach(
            @RequestParam("tenSach") String tenSach,
            @RequestParam("theLoais") Set<TheLoai> theLoais,
            @RequestParam("tacgia") Set<TacGia> tacgia,
            @RequestParam("trangThai") String trangThai, // nho chuyen thanh int nhe
            @RequestParam("moTa") String moTa,
            @RequestParam("soLuongTonKho") String soLuongTonKho,
            @RequestParam("giaBan") String giaBan,
            @RequestParam("maVach") String maVach,
            @RequestParam("linkAnh1") MultipartFile linkAnh1,
            @RequestParam("linkAnh2") MultipartFile linkAnh2,
            @RequestParam("linkAnh3") MultipartFile linkAnh3,
            @RequestParam("linkAnh4") MultipartFile linkAnh4,
            @RequestParam("linkAnh5") MultipartFile linkAnh5,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // validate nè

            List<String> listLoiValidate = new ArrayList<>();
            int countLoiValidate = 0;
            if (tenSach.trim().length() > 50 || tenSach.trim().length() == 0) {
                listLoiValidate.add("Tên ngắn dưới 50 kí tự và không để trống!");
                countLoiValidate = countLoiValidate + 1;
            }
            if (moTa.trim().length() > 250 || moTa.trim().length() == 0) {
                listLoiValidate.add("Mô tả dưới 250 kí tự và không để trống!");
                countLoiValidate = countLoiValidate + 1;
            }
            if (soLuongTonKho.trim().length() < 0) {
                listLoiValidate.add("Số lượng phải dương chứ!");
                countLoiValidate = countLoiValidate + 1;
            }
            if (giaBan.trim().length() == 0 || Double.parseDouble(giaBan.trim()) < 0) {
                listLoiValidate.add("Giá bán phải dương chứ, bán vậy lỗ!!");
                countLoiValidate = countLoiValidate + 1;
            }
            if (maVach.trim().length() != 12) {
                listLoiValidate.add("Mã vạch phải là dãy 12 số nha!");
                countLoiValidate = countLoiValidate + 1;
            }
            if (countLoiValidate > 0) {
                redirectAttributes.addFlashAttribute("danhSachLoiValidate", listLoiValidate);
                return "redirect:/quan-ly/sach/hien-thi";
            }

            String duongDanCotDinh = "/image/anhsanpham/";
            String duongDanLuuAnh1 = duongDanCotDinh + linkAnh1.getOriginalFilename();
            String duongDanLuuAnh2 = duongDanCotDinh + linkAnh2.getOriginalFilename();
            String duongDanLuuAnh3 = duongDanCotDinh + linkAnh3.getOriginalFilename();
            String duongDanLuuAnh4 = duongDanCotDinh + linkAnh4.getOriginalFilename();
            String duongDanLuuAnh5 = duongDanCotDinh + linkAnh5.getOriginalFilename();
            if (linkAnh1.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh1 = "";
            } else {
                byte[] bytes = linkAnh1.getBytes();
                Path path = Paths.get(uploadDirectory + linkAnh1.getOriginalFilename());
                Files.write(path, bytes);
            }
            if (linkAnh2.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh2 = "";
            } else {
                byte[] bytes2 = linkAnh2.getBytes();
                Path path2 = Paths.get(uploadDirectory + linkAnh2.getOriginalFilename());
                Files.write(path2, bytes2);
            }
            if (linkAnh3.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh3 = "";
            } else {
                byte[] bytes3 = linkAnh3.getBytes();
                Path path3 = Paths.get(uploadDirectory + linkAnh3.getOriginalFilename());
                Files.write(path3, bytes3);
            }
            if (linkAnh4.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh4 = "";
            } else {
                // luu anh 4
                byte[] bytes4 = linkAnh4.getBytes();
                Path path4 = Paths.get(uploadDirectory + linkAnh4.getOriginalFilename());
                Files.write(path4, bytes4);
            }
            if (linkAnh5.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh5 = "";
            } else {
                byte[] bytes5 = linkAnh5.getBytes();
                Path path5 = Paths.get(uploadDirectory + linkAnh5.getOriginalFilename());
                Files.write(path5, bytes5);
            }
            // xu ly lưu sách
            Sach sachFoundByMaVach = this.iSachService.getOneByMaVach(maVach);
            if (sachFoundByMaVach != null) {
                // ma vach da ton tai
                // thông báo
                redirectAttributes.addFlashAttribute("thongBaoMaVach", "Mã vạch " + sachFoundByMaVach.getMaVach() +
                        " đã tồn tại!, hãy cập nhật sản phẩm này hoặc thay đổi mã vạch mới!");
                System.out.println("in trước khi chuyển");
                return "redirect:/quan-ly/sach/hien-thi";
            } else {
                // ma vach chua ton tai
                BigDecimal giaBanOke = new BigDecimal(giaBan);
                Sach sach = Sach.builder()
                        .tenSach(tenSach)
                        .tacgia(tacgia)
                        .theLoais(theLoais)
                        .trangThai(Integer.parseInt(trangThai))
                        .moTa(moTa)
                        .soLuongTonKho(Integer.parseInt(soLuongTonKho))
                        .giaBan(giaBanOke)
                        .maVach(maVach)
                        .linkAnh1(duongDanLuuAnh1)
                        .linkAnh2(duongDanLuuAnh2)
                        .linkAnh3(duongDanLuuAnh3)
                        .linkAnh4(duongDanLuuAnh4)
                        .linkAnh5(duongDanLuuAnh5)
                        .build();
                this.iSachService.save(sach);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//sau khi them moi thanh cong, chuyen ve trang chu
        return "redirect:/quan-ly/sach/hien-thi";
    }

    @GetMapping("/anhtest")
    public String uploadForm() {
        return "uploadForm";
    }

    @PostMapping("/sach/cap-nhat")
    public String sachSua(@RequestParam("editlinkAnh1") MultipartFile fileAnh,
                          @RequestParam("checkthayDoiImage1") String trangThaiThayDoiAnh1,
                          @RequestParam("checkthayDoiImage2") String trangThaiThayDoiAnh2,
                          @RequestParam("checkthayDoiImage3") String trangThaiThayDoiAnh3,
                          @RequestParam("checkthayDoiImage4") String trangThaiThayDoiAnh4,
                          @RequestParam("checkthayDoiImage5") String trangThaiThayDoiAnh5,
                          @RequestParam("IdSach") String IdSach,
                          @RequestParam("tenSach") String tenSach,
                          @RequestParam("theLoais") Set<TheLoai> theLoais,
                          @RequestParam("tacgia") Set<TacGia> tacgia,
                          @RequestParam("trangThai") String trangThai, // nho chuyen thanh int nhe
                          @RequestParam("moTa") String moTa,
                          @RequestParam("maVach") String maVach,
                          @RequestParam("soLuongTonKho") String soLuongTonKho,
                          @RequestParam("giaBan") String giaBan,
                          @RequestParam("editlinkAnh1") MultipartFile linkAnh1,
                          @RequestParam("editlinkAnh2") MultipartFile linkAnh2,
                          @RequestParam("editlinkAnh3") MultipartFile linkAnh3,
                          @RequestParam("editlinkAnh4") MultipartFile linkAnh4,
                          @RequestParam("editlinkAnh5") MultipartFile linkAnh5,
                          HttpServletRequest request
    ) throws IOException {
        System.out.println(trangThaiThayDoiAnh1);
        System.out.println(trangThaiThayDoiAnh2);
        System.out.println(trangThaiThayDoiAnh3);
        System.out.println(trangThaiThayDoiAnh4);
        System.out.println(trangThaiThayDoiAnh5);
        Sach sach = this.iSachService.getOne(Integer.parseInt(IdSach));
        String duongDanCotDinh = "/image/anhsanpham/";
        String duongDanLuuAnh1 = "";
        String duongDanLuuAnh2 = "";
        String duongDanLuuAnh3 = "";
        String duongDanLuuAnh4 = "";
        String duongDanLuuAnh5 = "";
        // kiem tra xem link anh bị thay doi khong
        if (trangThaiThayDoiAnh1.equalsIgnoreCase("DaThayDoi")) {
            // anh 1 da thay doi, luu lai anh vao  o
            if (linkAnh1.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh1 = "";
            } else {
                byte[] bytes = linkAnh1.getBytes();
                Path path = Paths.get(uploadDirectory + linkAnh1.getOriginalFilename());
                Files.write(path, bytes);
                duongDanLuuAnh1 = duongDanCotDinh + linkAnh1.getOriginalFilename();
            }
        } else {
            // anh chua thay doi, lay lai duong dan cu
            duongDanLuuAnh1 = sach.getLinkAnh1();
        }
//        2
        if (trangThaiThayDoiAnh2.equalsIgnoreCase("DaThayDoi")) {
            // anh 1 da thay doi, luu lai anh vao  o
            if (linkAnh2.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh2 = "";
            } else {
                byte[] bytes2 = linkAnh2.getBytes();
                Path path2 = Paths.get(uploadDirectory + linkAnh2.getOriginalFilename());
                Files.write(path2, bytes2);
                duongDanLuuAnh2 = duongDanCotDinh + linkAnh2.getOriginalFilename();

            }
        } else {
            // anh chua thay doi, lay lai duong dan cu
            duongDanLuuAnh2 = sach.getLinkAnh2();
        }

// 3
        if (trangThaiThayDoiAnh3.equalsIgnoreCase("DaThayDoi")) {
            // anh 1 da thay doi, luu lai anh vao  o
            if (linkAnh3.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh3 = "";
            } else {
                byte[] bytes3 = linkAnh3.getBytes();
                Path path3 = Paths.get(uploadDirectory + linkAnh3.getOriginalFilename());
                Files.write(path3, bytes3);
                duongDanLuuAnh3 = duongDanCotDinh + linkAnh3.getOriginalFilename();
            }
        } else {
            // anh chua thay doi, lay lai duong dan cu
            duongDanLuuAnh3 = sach.getLinkAnh3();
        }
//         4
        if (trangThaiThayDoiAnh4.equalsIgnoreCase("DaThayDoi")) {
            // anh 1 da thay doi, luu lai anh vao  o
            if (linkAnh4.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh4 = "";
            } else {
                // luu anh 4
                byte[] bytes4 = linkAnh4.getBytes();
                Path path4 = Paths.get(uploadDirectory + linkAnh4.getOriginalFilename());
                Files.write(path4, bytes4);
                duongDanLuuAnh4 = duongDanCotDinh + linkAnh4.getOriginalFilename();
            }
        } else {
            // anh chua thay doi, lay lai duong dan cu
            duongDanLuuAnh4 = sach.getLinkAnh3();
        }
        // 5
        if (trangThaiThayDoiAnh5.equalsIgnoreCase("DaThayDoi")) {
            // anh 1 da thay doi, luu lai anh vao  o
            if (linkAnh5.isEmpty()) {
                // Xử lý lỗi khi tệp rỗng
                duongDanLuuAnh5 = "";
            } else {
                byte[] bytes5 = linkAnh5.getBytes();
                Path path5 = Paths.get(uploadDirectory + linkAnh5.getOriginalFilename());
                Files.write(path5, bytes5);
                duongDanLuuAnh5 = duongDanCotDinh + linkAnh5.getOriginalFilename();
            }

        } else {
            // anh chua thay doi, lay lai duong dan cu
            duongDanLuuAnh5 = sach.getLinkAnh5();
        }
        // xu ly lưu sách
        // xu  ly so luong
        Integer.parseInt(soLuongTonKho);
        BigDecimal giaBanOke = new BigDecimal(giaBan);
        Sach sachUpDate = Sach.builder()
                .idSach(Integer.parseInt(IdSach))
                .tenSach(tenSach)
                .tacgia(tacgia)
                .theLoais(theLoais)
                .trangThai(Integer.parseInt(trangThai))
                .moTa(moTa)
                .soLuongTonKho(Integer.parseInt(soLuongTonKho))
                .giaBan(giaBanOke)
                .maVach(maVach)
                .linkAnh1(duongDanLuuAnh1)
                .linkAnh2(duongDanLuuAnh2)
                .linkAnh3(duongDanLuuAnh3)
                .linkAnh4(duongDanLuuAnh4)
                .linkAnh5(duongDanLuuAnh5)
                .build();
        this.iSachService.save(sachUpDate);
        return "redirect:/quan-ly/sach/hien-thi";

    }

}

