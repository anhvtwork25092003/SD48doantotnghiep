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
@Table(name="thudientudoituong")
public class ThuDienTuDoiTuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthudientudoituong")
    private Integer idThuDienTuDoiTuong;

    @Column(name = "idthudientu")
    private Integer idThuDienTu;

    @Column(name = "idkhachhang")
    private Integer idKhachHang;

}
