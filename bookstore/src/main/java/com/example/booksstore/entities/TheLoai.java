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
    @Column(name = "idtheloai")
    private Integer idTheLoai;
    @Column(name = "tentheloai")
    private String tenTheLoai;
    @Column(name = "mota")
    private String moTa;
    @Column(name = "trangthai")
    private Integer trangThai;

}
