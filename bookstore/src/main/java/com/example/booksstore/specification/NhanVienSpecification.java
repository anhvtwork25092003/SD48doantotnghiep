package com.example.booksstore.specification;

import com.example.booksstore.entities.NhanVien;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NhanVienSpecification {
    public static Specification<NhanVien> filterNhanVien(String hoVaTen, String maNhanVien, Integer trangThai) {
        return (root, query, criteriaBuilder) -> {
            // Tạo một danh sách các điều kiện tìm kiếm
            List<Predicate> predicates = new ArrayList<>();

            // Tên nhân viên
            if (hoVaTen != null && !hoVaTen.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("hoVaTen"), "%" + hoVaTen + "%"));
            }

            // Mã Nhân Viên
            if (maNhanVien != null && !maNhanVien.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("maNhanVien"), maNhanVien));
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
