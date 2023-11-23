package com.example.booksstore.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "donhang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddonhang")
    private Integer idDonHang;

    @Column(name = "madonhang")
    private String maDonHang;

    @Column(name = "ngaytao")
    private java.util.Date ngayTao;

    @Column(name = "ngaythanhtoan")
    private Date ngayThanhToan;


    @Column(name = "phivanchuyen")
    private BigDecimal phiVanChuyen;


    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "trangthaithanhtoan")
    private Integer trangThaithanhtoan;

    @Column(name = "ghichukhachhanggui")
    private String ghiChuKhachHangui;

    @Column(name = "ghichulydodonhang")
    private String ghiChuLyDoDonHang;

    @ManyToOne
    @JoinColumn(name = "idkhachhang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "idphuongthucthanhtoan")
    private PhuongThucThanhToan phuongThucThanhToan;

    @ManyToOne
    @JoinColumn(name = "idnhanvien")
    private NhanVien nhanVien;


    @ManyToOne
    @JoinColumn(name = "thongtingiaohang")
    private ThongTinGiaoHang thongTinGiaoHang;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL)
    private List<DonHangChiTiet> chiTietDonHang;
}

