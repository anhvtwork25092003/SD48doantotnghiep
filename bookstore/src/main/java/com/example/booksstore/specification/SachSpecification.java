package com.example.booksstore.specification;


import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SachSpecification {
    public static Specification<Sach> filterSach(String tenSach, String maSach, BigDecimal giaMin, BigDecimal giaMax,
                                                 Set<TheLoai> theLoai, Integer trangThai) {
        return (root, query, criteriaBuilder) -> {
            // Tạo một danh sách các điều kiện tìm kiếm
            List<Predicate> predicates = new ArrayList<>();

            // Tên sách
            if (tenSach != null && !tenSach.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("tenSach"), "%" + tenSach + "%"));
            }

            // Mã sách
            if (maSach != null && !maSach.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("maSach"), maSach));
            }

            // Giá sách trong khoảng từ giaMin đến giaMax
            if (giaMin != null && giaMax != null) {
                predicates.add(criteriaBuilder.between(root.get("giaBan"), giaMin, giaMax));
            }

            // Thể loại sách (nếu được chọn)
            if (theLoai != null && !theLoai.isEmpty()) {
                List<Predicate> orPredicates = new ArrayList<>();

                for (TheLoai selectedTheLoai : theLoai) {
                    orPredicates.add(criteriaBuilder.isMember(selectedTheLoai, root.get("theLoais")));
                }

                Predicate finalOrPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
                predicates.add(finalOrPredicate);
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
