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
@Table(name="SachTheLoai")
public class SachTheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSachTheLoai")
    private Integer IdSachTheLoai;

    @ManyToOne
    @JoinColumn(name = "IdSach")
    private Sach sach;
    @ManyToOne
    @JoinColumn(name = "IdTheLoai")
    private TheLoai theLoai;
}
