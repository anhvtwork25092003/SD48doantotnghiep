package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.service.ISachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/quan-ly")
public class QuanLySachController {

    @Autowired
    ISachService iSachService;

    @GetMapping("/sach/hien-thi")
    public String hienThiTrangTongQuanQuanLy(Model model, @RequestParam(defaultValue = "1") int page ){

        int pageSize = 20; // Đặt kích thước trang mặc định

        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0

        Page<Sach> pageOfSach = iSachService.pageOfSach(pageable);

        model.addAttribute("pageOfSach", pageOfSach);

        return "user/sanpham/sanpham";
    }


    @Value("${upload.directory}")
    private String uploadDirectory;

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
