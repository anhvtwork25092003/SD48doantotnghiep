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
@Table(name = "tacgia")
public class TacGia {
    @Id
    @Column(name = "idtacgia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdTacGia;

    @Column(name = "email")
    private String Email;

    @Column(name = "hovaten")
    private String HoVaTen;

    @Column(name = "linkanhtacgia")
    private String LinkAnhTacGia;

    @Column(name = "trangthai")
    private Integer TrangThai;

    @Column(name = "sdt")
    private String SDT;

}
