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
@Table(name="theloai")
public class TheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTheLoai")
    private Integer IdTheLoai;
    @Column(name = "tentheloai")
    private String TenTheLoai;
    @Column(name = "mota")
    private String MoTa;
    @Column(name = "trangthai")
    private Integer TrangThai;

}
