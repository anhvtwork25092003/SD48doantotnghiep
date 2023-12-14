package com.example.booksstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopSanPhamDTO {
    private int idSach;
    private String tenSanPham;
    private Long soLuong;
}
