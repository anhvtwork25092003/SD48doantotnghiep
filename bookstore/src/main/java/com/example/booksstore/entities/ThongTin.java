package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name="thongtin")
public class ThongTin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtin")
    private Integer idThongTin;

    @Column(name = "linkbannertrangchu")
    private String linkBannerTrangChu;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "sodienthoai1")
    private String soDienThoai1;

    @Column(name = "sodienthoai2")
    private String soDienThoai2;

    @Column(name = "linklogo")
    private String linkLogo;

    @Column(name = "email")
    private String email;

}
