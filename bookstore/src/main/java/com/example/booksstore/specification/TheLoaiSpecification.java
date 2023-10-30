package com.example.booksstore.specification;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

public class TheLoaiSpecification {
    public static Specification<TheLoai> filterTheLoai(String tentheloai,Integer trangThai) {
        return (root, query, criteriaBuilder) -> {
            // Tạo một danh sách các điều kiện tìm kiếm
            List<Predicate> predicates = new ArrayList<>();

            // Tên sách
            if (tentheloai != null && !tentheloai.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("tenTheLoai"), "%" + tentheloai + "%"));
            }

            if (trangThai != -1) {
                if (trangThai == 1) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 1)); // Đang hoạt động
                } else if (trangThai == 0) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 0)); // Đã ngừng hoạt động
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
