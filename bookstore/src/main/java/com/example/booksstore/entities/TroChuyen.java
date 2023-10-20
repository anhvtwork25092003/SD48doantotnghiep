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
@Table(name="trochuyen")
public class TroChuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtrochuyen")
    private Integer idTroChuyen;

    @Column(name = "idkhachhang")
    private Integer idKhachHang;

    @Column(name = "idnhanvien")
    private Integer idNhanVien;

    @Column(name = "noidung")
    private String NoiDung;

    @Column(name = "ngaygui")
    private Date ngayGui;
}
