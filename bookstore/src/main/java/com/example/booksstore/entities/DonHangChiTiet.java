package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="donhangchitiet")
public class DonHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddonhangchitiet")
    private Integer idDonHangChiTiet;

    @Column(name = "idsach")
    private Integer idSach;

    @Column(name = "iddonhang")
    private Integer idDonHang;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "idkhuyenmai")
    private Integer idKhuyenMai;

    @Column(name = "giagoc")
    private BigDecimal giaGoc;

    @Column(name = "dongiathoidiemmua")
    private Integer donGiaThoiDiemMua;

    @Column(name = "phantramgiam")
    private Integer phanTramGiam;

    @Column(name = "thanhtien")
    private BigDecimal thanhTien;
}

