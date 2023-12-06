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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    @Temporal(TemporalType.TIMESTAMP)
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

    public BigDecimal tinhDoanhThu() {
        // Logic tính doanh thu của đơn hàng
        // Bạn có thể tính doanh thu bằng cách lấy tổng giá trị của các đơn hàng chi tiết
        BigDecimal doanhThu = BigDecimal.ZERO;
        if (chiTietDonHang != null) {
            for (DonHangChiTiet chiTiet : chiTietDonHang) {
                doanhThu = doanhThu.add(chiTiet.getThanhTien());
            }
        }
        return doanhThu;
    }
}

