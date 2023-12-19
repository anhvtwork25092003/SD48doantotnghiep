package com.example.booksstore.specification;


import com.example.booksstore.entities.TraHang;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DoiHangSpecification {
    public static Specification<TraHang> filterTraHang(Integer idTraHang, Integer trangThai) {
        return (root, query, criteriaBuilder) -> {
            // Tạo một danh sách các điều kiện tìm kiếm
            List<Predicate> predicates = new ArrayList<>();

            // Tên sách
            if (idTraHang != null) {
                predicates.add(criteriaBuilder.like(root.get("idTraHang"), "%" + idTraHang + "%"));
            }
            if(trangThai != -1) {
                if(trangThai == 1) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 1)); // Đang hoạt động
                }else if (trangThai == 0){
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 0)); // Ngừng hoạt động
                }else if(trangThai == 2){
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"),2 ));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
