package com.example.booksstore.datatranferenceobjects.request;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@ToString
public class SachRequest {

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
}
