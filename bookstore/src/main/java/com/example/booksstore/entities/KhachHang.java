package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@Table(name="khachhang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhachhang")
    private Integer idKhachHang;

    @Column(name = "idgiohang")
    private Integer idGioHang;

    @Column(name = "makhachhang")
    private String maKhachHang;

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

    @Column(name = "loaikhachhang")
    private String loaiKhachHang;

    @Column(name = "gioitinh")
    private Integer gioiTinh;

    @Column(name = "ngaytaotaikhoan")
    private Date ngayTaoTaiKhoan;
}

