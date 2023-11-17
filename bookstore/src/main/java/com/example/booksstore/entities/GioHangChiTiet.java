package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name="giohangchitiet")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgiohangchitiet")
    private Integer idGioHangChiTiet;

    @ManyToOne
    @JoinColumn(name = "idgiohang")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "idsach")
    private Sach sach;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "ngaychinhsua")
    private Date ngayChinhSua;

}

