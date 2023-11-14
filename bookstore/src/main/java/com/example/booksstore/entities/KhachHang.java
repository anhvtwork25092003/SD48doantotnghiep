package com.example.booksstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name="khachhang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhachhang")
    private Integer idKhachHang;

    @Column(name = "idgiohang")
    private Integer idGioHang;
    @Generated
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


    @JsonIgnore
    @ManyToMany(mappedBy = "khachHangsforthongBaoKhuyenMai")
    private Set<ThongBao> thongBaos = new HashSet<>();
}

