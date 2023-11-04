package com.example.booksstore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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


    @Column(name = "sophantramgiamgia")
    private Integer soPhanTramGiamGia;

    @ManyToOne
    @JoinColumn(name = "idsach")
    private Sach sachList;

    @ManyToOne
    @JoinColumn(name = "idkhuyenmai")
    private KhuyenMai khuyenmaiList;


}
