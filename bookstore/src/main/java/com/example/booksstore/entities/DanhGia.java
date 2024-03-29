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

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "danhgia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddanhgia")
    private Integer idDanhGia;

    @ManyToOne
    @JoinColumn(name = "idkhachhang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "idsach")
    private Sach sach;

    @Column(name = "mucdanhgia")
    private Integer mucDanhGia;

    @Column(name = "binhluan")
    private String binhLuan;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "trangthai")
    private Integer trangThai;


}
