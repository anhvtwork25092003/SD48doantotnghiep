package com.example.booksstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="thongbao")
@Builder
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongbao")
    private Integer idThongBao;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "ngaygui")
    private Date ngayGui;



    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "thongbaokhachhang",
            joinColumns = @JoinColumn(name = "idthongbao"),
            inverseJoinColumns = @JoinColumn(name = "idkhachhang")
    )
    private Set<KhachHang> khachHangsforthongBaoKhuyenMai = new HashSet<>();
}
