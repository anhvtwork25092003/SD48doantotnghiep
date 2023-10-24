package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.service.TacGiaService;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Controller
@RequestMapping("/quan-ly")
public class QuanLySachController {

    @Autowired
    ISachService iSachService;

    @Autowired
    TacGiaService tacGiaService;

    @Autowired
    ITheLoaiServiec iTheLoaiService;
    @Value("${upload.directory}")
    private String uploadDirectory;

    @GetMapping("/sach/hien-thi")
    public String hienThiTrangTongQuanQuanLy(Model model, @RequestParam(defaultValue = "1") int page) {

        int pageSize = 3; // Đặt kích thước trang mặc định

        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0

        Page<Sach> pageOfSach = iSachService.pageOfSach(pageable);

        model.addAttribute("pageOfSach", pageOfSach);
        model.addAttribute("authors", tacGiaService.findAllTacGia());
        model.addAttribute("listTheLoai", iTheLoaiService.fillAll());
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
            @RequestParam("soLuongTonKho") String maVach,
            @RequestParam("linkAnh1") MultipartFile linkAnh1,
            @RequestParam("linkAnh2") MultipartFile linkAnh2,
            @RequestParam("linkAnh3") MultipartFile linkAnh3,
            @RequestParam("linkAnh4") MultipartFile linkAnh4,
            @RequestParam("linkAnh5") MultipartFile linkAnh5
    ) {
        try {

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
            System.out.println(duongDanCotDinh + linkAnh1.getOriginalFilename());
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

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        System.out.println(file);
        if (file.isEmpty()) {
            // Xử lý lỗi khi tệp rỗng
            System.out.println("tep rong");
            return "redirect:/anhtest";
        }
        try {
            // Lưu tệp vào thư mục B
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println("thanh cong");

        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi khi lưu tệp thất bại
            System.out.println("loi xu ly tep");

            return "redirect:/anhtest";
        }

        // Điều hướng người dùng đến trang web gốc với thông báo thành công
        return "redirect:/anhtest";
    }

}
