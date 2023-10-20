package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="donvivanvhuyen")
public class DonViVanChuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddonvivanvhuyen")
    private Integer idDonViVanChuyen;

    @Column(name = "tenphuongthucthanhtoan")
    private String tenPhuongThucThanhToan;

    @Column(name = "phivanchuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "email")
    private String email;
}

