package com.example.booksstore.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "thongtingiaohang")
public class ThongTinGiaoHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtingiaohang")
    private Integer idThongTinGiaoHang;

    @Column(name = "tennguoinhan")
    private String tenNguoiNhan;

    @Column(name = "sodienthoai")
    private String sdt;

    @Column(name = "thanhpho")
    private String thanhPho;

    @Column(name = "quanhuyen")
    private String quanHuyen;

    @Column(name = "phuongxa")
    private String phuongXa;
    @Column(name = "diachichu")
    private String diaChiChu;
    @Column(name = "diachicuthe")
    private String diaChiCuThe;
    @Column(name = "emailgiaohang")
    private String emailGiaoHang;

    @OneToMany(mappedBy = "thongTinGiaoHang", cascade = CascadeType.ALL)
    private List<DonHang> donHang;
}

