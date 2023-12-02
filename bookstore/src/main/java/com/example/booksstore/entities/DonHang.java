package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

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

    @Generated
    @Column(name = "madonhang")
    private String maDonHang;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngayhuy")
    private Date ngayHuy;

    @Column(name = "ngaythanhtoan")
    private Date ngayThanhToan;


    @Column(name = "phivanchuyen")
    private BigDecimal phiVanChuyen;


    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "trangthaithanhtoan")
    private Integer trangThaithanhtoan;

    @Column(name = "ghichukhachhanggui")
    private String ghiChuKhachHangGui;

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
    @JoinColumn(name = "idthongtingiaohang")
    private ThongTinGiaoHang thongTinGiaoHang;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL)
    private List<DonHangChiTiet> chiTietDonHang;
}

