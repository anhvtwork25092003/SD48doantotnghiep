package com.example.booksstore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "thongtin")
public class ThongTin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtin")
    private Integer idThongTin;

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
