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
@Table(name = "sachtacgia")
public class SachTacGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsachtacgia")
    private Integer IdSachTacGia;


    @ManyToOne
    @JoinColumn(name = "idsach")
    private Sach sach;
    @ManyToOne
    @JoinColumn(name = "idsacgia")
    private TacGia tacgia;


}
