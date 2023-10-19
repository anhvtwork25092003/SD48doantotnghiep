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
@Table(name="SachTacGia")
public class SachTacGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSachtacGia")
    private Integer IdSachtacGia;


    @ManyToOne
    @JoinColumn(name = "IdSach")
    private Sach sach;
    @ManyToOne
    @JoinColumn(name = "IdTacGia")
    private TacGia tacGia;


}
