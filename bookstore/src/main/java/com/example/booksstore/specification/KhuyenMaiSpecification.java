package com.example.booksstore.specification;

import com.example.booksstore.entities.KhuyenMai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhuyenMaiSpecification {
    public  static Specification<KhuyenMai> filterKhuyenMai(String tenKhuyenMai, Integer trangThai){
        return (root, query, criteriaBuilder) ->  {
            List<Predicate> predicates = new ArrayList<>();
            //tên khuyến mại
            if (tenKhuyenMai !=null && !tenKhuyenMai.isEmpty() ) {
                predicates.add(criteriaBuilder.equal(root.get("tenKhuyenMai"), tenKhuyenMai));
            }

//            if (ngayBatDau != null) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngayBatDau"), ngayBatDau));
//            }
//
//            // Ngày kết thúc
//            if (ngayKetThuc != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngayKetThuc"), ngayKetThuc));
//            }

                if(trangThai == 1) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 1)); // Đang hoạt động
                }else if (trangThai == 0){
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 0)); // Ngừng hoạt động
                }

            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
