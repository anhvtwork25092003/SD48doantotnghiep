package com.example.booksstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "TacGia")
public class TacGia {
    @Id
    @Column(name = "IdTacGia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdTacGia;

    @Column(name = "Email")
    private String Email;

    @Column(name = "HoVaTen")
    private String HoVaTen;

    @Column(name = "LinkAnhTacGia")
    private String LinkAnhTacGia;

    @Column(name = "TrangThai")
    private Integer TrangThai;

    @Column(name = "SDT")
    private String SDT;

}
