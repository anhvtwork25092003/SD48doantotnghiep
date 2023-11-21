package com.example.booksstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;

import java.sql.Date;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "khachHangsforthongBaoKhuyenMai")
    private Set<ThongBao> thongBaos = new HashSet<>();

    @OneToMany(mappedBy = "khachHangDiaChi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaChi> diaChiList;
}

