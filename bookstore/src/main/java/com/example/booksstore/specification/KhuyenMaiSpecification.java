package com.example.booksstore.specification;

import com.example.booksstore.entities.KhuyenMai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhuyenMaiSpecification {
    public  static Specification<KhuyenMai> filterKhuyenMai(String tenKhuyenMai,Date ngayBatDau , Date ngayKetThuc, Integer trangThai){
        return (root, query, criteriaBuilder) ->  {
            List<Predicate> predicates = new ArrayList<>();
            //tên khuyến mại
            if (tenKhuyenMai !=null && !tenKhuyenMai.isEmpty() ) {
                predicates.add(criteriaBuilder.equal(root.get("tenKhuyenMai"), tenKhuyenMai));
            }

            if (ngayBatDau != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngayBatDau"), ngayBatDau));
            }

            // Ngày kết thúc
            if (ngayKetThuc != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngayKetThuc"), ngayKetThuc));
            }

                if(trangThai == 1) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 1)); // Đang hoạt động
                }else{
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 0)); // Ngừng hoạt động
                }

//            LocalDate today = LocalDate.now();
//            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngayBatDau"), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())));
//            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngayKetThuc"), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())));

            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
