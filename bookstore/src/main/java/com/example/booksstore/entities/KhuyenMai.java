package com.example.booksstore.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
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

    @Column(name = "sophantramgiamgia")
    private Integer soPhanTramGiamGia;
    @Column(name = "trangthaihienthi")
    private Integer trangThaiHienThi;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "sachkhuyenmai",
            joinColumns = @JoinColumn(name = "idkhuyenmai"),
            inverseJoinColumns = @JoinColumn(name = "idsach")
    )
    private Set<Sach> sachs = new HashSet<>();

    // update trang thai ap dung khuyen mai
    public void updateTrangThai() {
        Date currentDate = new Date();
        if (currentDate.after(ngayBatDau) && currentDate.before(ngayKetThuc)) {
            trangThai = 1;
        } else {
            trangThai = 0;
        }
    }
}
