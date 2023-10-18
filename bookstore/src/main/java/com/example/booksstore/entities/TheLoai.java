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
@Table(name="TheLoai")
public class TheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTheLoai")
    private Integer IdTheLoai;
    @Column(name = "TenTheLoai")
    private String TenTheLoai;
    @Column(name = "MoTa")
    private String MoTa;
    @Column(name = "TrangThai")
    private Integer TrangThai;

}
