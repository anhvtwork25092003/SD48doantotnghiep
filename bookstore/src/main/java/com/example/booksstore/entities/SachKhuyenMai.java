package com.example.booksstore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sachkhuyenmai")
public class SachKhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsachkhuyenmai")
    private Integer idSachKhuyenMai;

    @Column(name = "idsach")
    private Integer idSach;

    @Column(name = "idkhuyenmai")
    private Integer idKhuyenMai;

    @Column(name = "sophantramgiamgia")
    private Integer soPhanTramGiamGia;


}
