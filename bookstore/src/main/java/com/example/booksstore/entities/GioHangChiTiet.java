package com.example.booksstore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "giohangchitiet")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgiohangchitiet")
    private Integer idGioHangChiTiet;

    @ManyToOne
    @JoinColumn(name = "idgiohang")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "idsach")
    private Sach sach;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "ngaychinhsua")
    private Date ngayChinhSua;

    public String formatCurrency(BigDecimal amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(amount);
    }

    public String tinhThanhTien() {
        if (this.sach.layGiaNeuCoKhuyenMai() == null) {
            // return thành tiền với giá gốc, chuyển thành vnd
            BigDecimal thanhTien = this.sach.getGiaBan().multiply(BigDecimal.valueOf(this.soLuong));
            return formatCurrency(thanhTien);
        } else {
            // có khuyến mãi, return ra giá khuyến mãi
            BigDecimal thanhTien2 = this.sach.layGiaNeuCoKhuyenMaiBigdecimal().multiply(BigDecimal.valueOf(this.soLuong));
            return formatCurrency(thanhTien2);
        }
    }


}

