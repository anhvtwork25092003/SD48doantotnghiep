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

    @ManyToOne
    @JoinColumn(name = "idkhachhang")
    private KhachHang khachHang;
}
