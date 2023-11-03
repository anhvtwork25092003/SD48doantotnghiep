package com.example.booksstore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "khuyenmai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhuyenmai")
    private Integer idKhuyenMai;

    @Column(name = "tenkhuyenmai")
    private String tenKhuyenMai;

    @Column(name = "ngaybatdau")
    private Date ngayBatDau;

    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "linkbannerkhuyenmai")
    private String linkBannerKhuyenMai;

    @Column(name = "linkanhkhuyenmai")
    private String linkAnhKhuyenMai;

    @ManyToMany
    @JoinTable(
            name = "sachkhuyenmai",
            joinColumns = @JoinColumn(name = "idkhuyenmai"),
            inverseJoinColumns = @JoinColumn(name = "idsach")
    )
    private Set<Sach> sachs = new HashSet<>();

}
