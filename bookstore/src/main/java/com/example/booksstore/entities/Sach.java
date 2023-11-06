package com.example.booksstore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@ToString
@Builder
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsach")
    private Integer idSach;

    @Generated
    @Column(name = "masach")
    private String  maSach;

    @Column(name = "tensach")
    private String  tenSach;

    @Column(name = "mota")
    private String  moTa;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "soluongtonkho")
    private Integer soLuongTonKho;

    @Column(name = "giaban")
    private BigDecimal giaBan;

    @Column(name = "linkanh1")
    private String linkAnh1;

    @Column(name = "linkanh2")
    private String linkAnh2;

    @Column(name = "linkanh3")
    private String linkAnh3;

    @Column(name = "linkanh4")
    private String linkAnh4;

    @Column(name = "linkanh5")
    private String linkAnh5;

    @Column(name = "mavach")
    private String  maVach;
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


    @ManyToMany(mappedBy = "sachs")
    private Set<KhuyenMai> khuyenMais = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "sachkhuyenmai",
            joinColumns = @JoinColumn(name = "idsach"),
            inverseJoinColumns = @JoinColumn(name = "idkhuyenmai")
    )
    private Set<SachKhuyenMai> sachKhuyenMais = new HashSet<>();
}
