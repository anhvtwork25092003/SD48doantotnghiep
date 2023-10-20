package com.example.booksstore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="danhgia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddanhgia")
    private Integer idDanhGia;

    @Column(name = "idsach")
    private Integer  idSach;

    @Column(name = "idkhachhang")
    private Integer  idKhachHang;

    @Column(name = "mucdanhgia")
    private Integer  mucDanhGia;

    @Column(name = "binhluan")
    private String  binhLuan;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "trangthai")
    private Integer  trangThai;


}
