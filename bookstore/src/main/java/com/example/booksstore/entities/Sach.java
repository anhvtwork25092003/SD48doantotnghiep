package com.example.booksstore.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sach")
@Builder
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsach")
    private Integer idSach;

    @Generated
    @Column(name = "masach")
    private String maSach;

    @Column(name = "tensach")
    private String tenSach;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "soluongtonkho")
    private Integer soLuongTonKho;

    @Column(name = "giaban")
    private BigDecimal giaBan;

    @Column(name = "linkanh1")
    private String linkAnh1;

    @Column(name = "linkanh2")
    private String linkAnh2;

    @Column(name = "linkanh3")
    private String linkAnh3;

    @Column(name = "linkanh4")
    private String linkAnh4;

    @Column(name = "linkanh5")
    private String linkAnh5;

    @Column(name = "mavach")
    private String maVach;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "sachtheloai",
            joinColumns = @JoinColumn(name = "idsach"),
            inverseJoinColumns = @JoinColumn(name = "idtheloai")
    )
    private Set<TheLoai> theLoais = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "sachtacgia",
            joinColumns = @JoinColumn(name = "idsach"),
            inverseJoinColumns = @JoinColumn(name = "idtacgia")
    )
    private Set<TacGia> tacgia = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "sachs")
    private Set<KhuyenMai> khuyenMais = new HashSet<>();

    @OneToMany(mappedBy = "sach", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DanhGia> danhgias;

    public BigDecimal getGiaSauGiamGia(double phanTramGiamGia) {
        BigDecimal phanTram = BigDecimal.valueOf(phanTramGiamGia);
        BigDecimal heSoGiamGia = BigDecimal.ONE.subtract(phanTram.divide(BigDecimal.valueOf(100)));
        return giaBan.multiply(heSoGiamGia);
    }


    public String formatCurrency(BigDecimal amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(amount);
    }

    public String giaBanVnd() {
        return formatCurrency(this.giaBan);
    }

    public String layGiaNeuCoKhuyenMai() {
        for (KhuyenMai khuyenMai : this.khuyenMais) {
            if (khuyenMai.getTrangThai() == 1) {
                BigDecimal phanTram = BigDecimal.valueOf(khuyenMai.getSoPhanTramGiamGia());
                BigDecimal heSoGiamGia = BigDecimal.ONE.subtract(phanTram.divide(BigDecimal.valueOf(100)));
                return formatCurrency(giaBan.multiply(heSoGiamGia));
            }
        }
        return null;
    }

    public BigDecimal layGiaNeuCoKhuyenMaiDeTinhGioHAng() {
        for (KhuyenMai khuyenMai : this.khuyenMais) {
            if (khuyenMai.getTrangThai() == 1) {
                BigDecimal phanTram = BigDecimal.valueOf(khuyenMai.getSoPhanTramGiamGia());
                BigDecimal heSoGiamGia = BigDecimal.ONE.subtract(phanTram.divide(BigDecimal.valueOf(100)));
                return giaBan.multiply(heSoGiamGia);
            }
        }
        return null;
    }

    public Integer layPhanTramGiamNeuCoKhuyenMai() {
        for (KhuyenMai khuyenMai : this.khuyenMais) {
            if (khuyenMai.getTrangThai() == 1) {
                return khuyenMai.getSoPhanTramGiamGia();
            }
        }
        return null; // hoặc bạn có thể trả về giá trị mặc định tùy thuộc vào yêu cầu của bạn
    }


}
