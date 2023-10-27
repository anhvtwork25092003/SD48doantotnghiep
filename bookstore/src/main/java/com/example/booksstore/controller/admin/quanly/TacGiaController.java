package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.service.TacGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class TacGiaController {
    @Autowired
    TacGiaService ser;
    @Value("${upload.directory1}")
    private String uploadDirectory1;
    @GetMapping("/tac-gia/getall")
    public String GetIndex(Model model , @RequestParam(defaultValue = "1") int page){
        int pageSize=3;

        Pageable pageable= PageRequest.of(page-1,pageSize);

        Page<TacGia> pageOfTacGia=ser.pageOfTacGia(pageable);

        model.addAttribute("data",pageOfTacGia);
        return "admin/quanly/tacgia/thanh_tacgia";
    }

    @GetMapping("/tac-gia/getcreate")
    private  String getTacGiaCreate(Model model){
        model.addAttribute("tacgia",new TacGia());
        return "redirect:/tac-gia/getall";
    }

    @PostMapping("/tac-gia/add")
    private String CreateTacgia(
            @RequestParam("hoVaTen") String hoVaTen,
            @RequestParam("email") String email,
            @RequestParam("trangThai") Integer trangThai,
            @RequestParam("sdt") String sdt,
            @RequestParam("linkAnhTacGia") MultipartFile linkAnhTacGia) {
        try {
            String duongDanCotDinh = "/image/anhcuathanh";
            String duongDanAnhTacGia = duongDanCotDinh + linkAnhTacGia.getOriginalFilename();
            if (linkAnhTacGia.isEmpty()) {
                duongDanAnhTacGia = "";
            } else {
                byte[] bytes = linkAnhTacGia.getBytes();
                Path path = Paths.get(uploadDirectory1 + linkAnhTacGia.getOriginalFilename());
                Files.write(path, bytes);
            }
            TacGia tacGia = TacGia.builder()
                    .hoVaTen(hoVaTen)
                    .email(email)
                    .trangThai(trangThai)
                    .sdt(sdt)
                    .linkAnhTacGia(duongDanAnhTacGia) // Set the file path, not the MultipartFile
                    .build();
            this.ser.createTacGia(tacGia);
            System.out.println(duongDanCotDinh + linkAnhTacGia.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/tac-gia/getall";
    }


    @GetMapping("/tac-gia/getct/{ma}")
    private String getCt(@PathVariable("ma") Integer ma, TacGia tacGia, Model model){
        TacGia t=ser.GetTacGiaByID(ma);
        model.addAttribute("tacgia",t);
        return "";
    }
    @PostMapping("/tac-gia/edit/")
    public String Edit(@RequestParam("editLinkAnhTacGia")MultipartFile fileAnhTacGia){
        String ketqua = "";
        System.out.println("dấdnáiđá");

        if (fileAnhTacGia.isEmpty()) {
            System.out.println("aaaa");
            ketqua = "file ảnh trống";
            System.out.println("file ảnh trống");
        } else {
            System.out.println("sssss");
            ketqua = fileAnhTacGia.getOriginalFilename();
            System.out.println(fileAnhTacGia.getOriginalFilename());
        }
        return "";
    }




}
