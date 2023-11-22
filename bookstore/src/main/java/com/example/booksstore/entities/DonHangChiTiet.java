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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "donhangchitiet")
public class DonHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddonhangchitiet")
    private Integer idDonHangChiTiet;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "giagoc")
    private BigDecimal giaGoc;

    @Column(name = "dongiathoidiemmua")
    private BigDecimal donGiaThoiDiemMua;

    @Column(name = "phantramgiam")
    private Integer phanTramGiam;

    @Column(name = "thanhtien")
    private BigDecimal thanhTien;

    @ManyToOne
    @JoinColumn(name = "iddonhang")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "idsach")
    private Sach sach;

}

