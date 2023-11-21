package com.example.booksstore.specification;


import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TimKiemSachSpecification {
    public static Specification<Sach> filterTimKiemSach(String tenSach, BigDecimal giaMin, BigDecimal giaMax,
                                                 Set<TheLoai> theLoai) {
        return (root, query, criteriaBuilder) -> {
            // Tạo một danh sách các điều kiện tìm kiếm
            List<Predicate> predicates = new ArrayList<>();

            // Tên sách
            if (tenSach != null && !tenSach.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("tenSach"), "%" + tenSach + "%"));
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

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
