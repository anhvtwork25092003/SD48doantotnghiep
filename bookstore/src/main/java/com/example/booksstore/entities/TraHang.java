package com.example.booksstore.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "trahang")
public class TraHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtrahang")
    private Integer idTraHang;

    @ManyToOne
    @JoinColumn(name = "iddonhang")
    private DonHang donHang;

    @Column(name = "ngaytao")
    private Date ngayTao;


    // 0- đang chuẩn bị hang-chờ vận chuyển gioa hàng
//1- đang giao hàng
//    2: thành công
//  3   hủy- giao không thành công
    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "traHang", cascade = CascadeType.ALL)
    private List<TraHangChiTiet> traHangChiTietList;
}
