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
@Table(name="sach")
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsach")
    private Integer IdSach;

    @Generated
    @Column(name = "masach")
    private String  MaSach;

    @Column(name = "mota")
    private String  MoTa;

    @Column(name = "trangthai")
    private Integer TrangThai;

    @Column(name = "soluongtonkho")
    private Integer SoLuongTonKho;

    @Column(name = "giaban")
    private BigDecimal GiaBan;

    @Column(name = "linkanh1")
    private String LinkAnh1;

    @Column(name = "linkanh2")
    private String LinkAnh2;

    @Column(name = "linkanh3")
    private String LinkAnh3;

    @Column(name = "linkanh4")
    private String LinkAnh4;

    @Column(name = "linkanh5")
    private String LinkAnh5;

    @Column(name = "mavach")
    private String  MaVach;
    @ManyToMany
    @JoinTable(
            name = "sachtheloai",
            joinColumns = @JoinColumn(name = "idsach"),
            inverseJoinColumns = @JoinColumn(name = "idtheloai")
    )
    private Set<TheLoai> theLoais = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "sachtacgia",
            joinColumns = @JoinColumn(name = "idsach"),
            inverseJoinColumns = @JoinColumn(name = "idtacgia")
    )
    private Set<TacGia> tacgia = new HashSet<>();
}
