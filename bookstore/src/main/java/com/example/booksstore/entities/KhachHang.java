package com.example.booksstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "khachhang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhachhang")
    private Integer idKhachHang;
    @Generated
    @Column(name = "makhachhang")
    private String maKhachHang;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "sdt")
    private String sdt;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    @OneToOne(mappedBy = "khachHang", cascade = CascadeType.ALL)
    private GioHang gioHang;


    @OneToMany(mappedBy = "khachHangDiaChi")
    private List<DiaChi> diaChiList;
    @OneToMany(mappedBy = "khachHang")
    private List<DonHang> donHang;


}

