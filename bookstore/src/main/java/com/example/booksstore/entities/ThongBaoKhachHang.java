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
@Table(name="thongbaokhachhang")
public class ThongBaoKhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongbaokhachhang")
    private Integer idThongBaoKhachHang;

    @Column(name = "idthongbao")
    private Integer idThongBao;

    @Column(name = "idkhachhang")
    private Integer idKhachHang;

//    @ManyToOne
//    @JoinColumn(name = "idkhachhang")
//    private KhachHang khachHang;
//
//    @ManyToOne
//    @JoinColumn(name = "idthongbao")
//    private ThongBao thongBao;

}
