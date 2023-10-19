package com.example.booksstore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Sach")
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSach")
    private Integer IdSach;

    @Generated
    @Column(name = "MaSach")
    private String  MaSach;

    @Column(name = "MoTa")
    private String  MoTa;

    @Column(name = "TrangThai")
    private Integer TrangThai;

    @Column(name = "SoLuongTonKho")
    private Integer SoLuongTonKho;

    @Column(name = "GiaBan")
    private BigDecimal GiaBan;

    @Column(name = "LinkAnh1")
    private String LinkAnh1;

    @Column(name = "LinkAnh2")
    private String LinkAnh2;

    @Column(name = "LinkAnh3")
    private String LinkAnh3;

    @Column(name = "LinkAnh4")
    private String LinkAnh4;

    @Column(name = "LinkAnh5")
    private String LinkAnh5;

    @Column(name = "MaVach")
    private String  MaVach;
    @ManyToMany
    @JoinTable(
            name = "SachTheLoai",
            joinColumns = @JoinColumn(name = "IdSach"),
            inverseJoinColumns = @JoinColumn(name = "IdTheLoai")
    )
    private Set<TheLoai> theLoais = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "SachTacGia",
            joinColumns = @JoinColumn(name = "IdSach"),
            inverseJoinColumns = @JoinColumn(name = "IdTacGia")
    )
    private Set<TacGia> tacgia = new HashSet<>();
}
