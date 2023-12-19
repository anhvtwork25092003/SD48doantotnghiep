package com.example.booksstore.specification;


import com.example.booksstore.entities.DonHang;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHangSpecification {
    public static Specification<DonHang> filterDonHang(String maDonHang, Date startDate, Date endDate, int trangThai) {
        return (root, query, criteriaBuilder) -> {
            // Tạo một danh sách các điều kiện tìm kiếm
            List<Predicate> predicates = new ArrayList<>();

            // Tên sách
            if (maDonHang != null && !maDonHang.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("maDonHang"), "%" + maDonHang + "%"));
            }   // Tên sách

            predicates.add(criteriaBuilder.equal(root.get("trangThai"), trangThai)); // Đang hoạt động

            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("ngayTao"), startDate, endDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
