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
@Table(name="sachtheloai")
public class SachTheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsachtheloai")
    private Integer idSachTheLoai;

    @ManyToOne
    @JoinColumn(name = "idsach")
    private Sach sach;
    @ManyToOne
    @JoinColumn(name = "idtheloai")
    private TheLoai theLoai;
}
