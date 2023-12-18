package com.example.booksstore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "diachi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiachi")
    private Integer idDiaChi;

    @Column(name = "tinhthanhpho")
    private String tinhThanhPho;

    @Column(name = "huyenquan")
    private String huyenQuan;

    @Column(name = "xaphuong")
    private String xaPhuong;

    @Column(name = "diachicuthe")
    private String diaChiCuThe;
    @Column(name = "diachichu")
    private String diaChiChu;
    @Column(name = "tennguoinhan")
    private String tenNguoiNhan;
    @Column(name = "sdtnguoinhanhang")
    private String sdtNguoiNhanHang;

    @ManyToOne
    @JoinColumn(name = "idkhachhang")
    private KhachHang khachHangDiaChi;
}
