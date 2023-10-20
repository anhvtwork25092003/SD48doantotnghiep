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
@Table(name="phuongthucthanhtoan")
public class PhuongThucThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphuongthucthanhtoan")
    private Integer idPhuongThucThanhToan;

    @Column(name = "tenphuongthucthanhtoan")
    private String tenPhuongThucThanhToan;

    @Column(name = "trangthai")
    private Integer trangThai;
}

