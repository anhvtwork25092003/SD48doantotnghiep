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
    private Integer idTacGia;

    @Column(name = "email")
    private String email;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "linkanhtacgia")
    private String linkAnhTacGia;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "sdt")
    private String sdt;

}
