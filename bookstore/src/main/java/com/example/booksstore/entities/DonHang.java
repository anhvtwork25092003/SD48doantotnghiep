package com.example.booksstore.entities;

import jakarta.persistence.*;
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
@Table(name="donhang")
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

    @Column(name = "tongtienhanggoc")
    private BigDecimal tongTienHangGoc;

    @Column(name = "tongtienkhuyenmai")
    private BigDecimal tongTienKhuyenMai;

    @Column(name = "phivanchuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "tongtiencanthanhtoan")
    private BigDecimal tongTienCanThanhToan;

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

