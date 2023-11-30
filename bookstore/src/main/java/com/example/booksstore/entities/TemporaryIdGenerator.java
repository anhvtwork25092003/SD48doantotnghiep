package com.example.booksstore.entities;

import java.util.Date;

public class TemporaryIdGenerator {
    public static Integer generateTemporaryId() {
        // Sử dụng timestamp làm ID tạm thời
        Date now = new Date();
        return (int) now.getTime(); // Chuyển đổi timestamp thành số nguyên
    }
}
