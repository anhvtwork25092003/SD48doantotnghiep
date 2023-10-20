package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="danhsachyeuthich")
public class DanhSachYeuThich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddanhsachyeuthich")
    private Integer idDanhSachYeuThich;

    @Column(name = "idkhachhang")
    private Integer idKhachHang;

    @Column(name = "idsach")
    private Integer idSach;
}
