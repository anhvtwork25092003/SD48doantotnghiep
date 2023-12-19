package com.example.booksstore.specification;

import com.example.booksstore.entities.KhuyenMai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhuyenMaiSpecification {
    public static Specification<KhuyenMai> filterKhuyenMai(String tenKhuyenMai, Date ngayBatDau, Date ngayKetThuc, Integer trangThai) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //tên khuyến mại
            if (tenKhuyenMai != null && !tenKhuyenMai.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("tenKhuyenMai"), tenKhuyenMai));
            }

            if (ngayBatDau != null && ngayKetThuc != null) {
                // Ngày bắt đầu phải nhỏ hơn ngày kết thúc
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngayBatDau"), ngayKetThuc));

                // Ngày kết thúc phải lớn hơn ngày bắt đầu
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngayKetThuc"), ngayBatDau));
            }
            if (trangThai != -1) {
                if (trangThai == 1) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 1)); // Đang hoạt động
                } else if (trangThai == 0) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 0)); // Ngừng hoạt động
                }
            }

//            LocalDate today = LocalDate.now();
//            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngayBatDau"), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())));
//            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngayKetThuc"), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
