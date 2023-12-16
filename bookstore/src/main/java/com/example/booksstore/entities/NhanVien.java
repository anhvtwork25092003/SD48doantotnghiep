package com.example.booksstore.entities;

import com.example.booksstore.service.NonBlankString;
import com.example.booksstore.service.NonNumericString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
@Table(name="nhanvien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnhanvien")
    private Integer idNhanVien;

    @Column(name = "manhanvien")
    private String maNhanVien;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "chucvu")
    private String chucVu;

    @Column(name = "linkanhnhanvien")
    private String linkAnhNhanVien;

}
