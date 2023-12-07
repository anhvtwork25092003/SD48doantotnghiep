package com.example.booksstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThongKeKhachHangResponse {

    private String tongKhachHang;
    private String khachVangLai;
    private String khachTaoTaiKhoan;

}
