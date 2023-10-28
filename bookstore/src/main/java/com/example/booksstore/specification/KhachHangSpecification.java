package com.example.booksstore.specification;

import com.example.booksstore.entities.KhachHang;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class KhachHangSpecification {
    public  static Specification<KhachHang> filterKhachHang(String maKhachHang, String sdt, Integer trangThai){
        return (root, query, criteriaBuilder) ->  {
            List<Predicate> predicates = new ArrayList<>();
            //mã khách hàng
            if (maKhachHang !=null && !maKhachHang.isEmpty() ) {
                predicates.add(criteriaBuilder.equal(root.get("maKhachHang"), maKhachHang));
            }

            // sdt khách hàng

            if (sdt !=null && !sdt.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("sdt"), sdt));
            }

            if(trangThai != -1) {
                if(trangThai == 1) {
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 1)); // Đang hoạt động
                }else if (trangThai == 0){
                    predicates.add(criteriaBuilder.equal(root.get("trangThai"), 0)); // Ngừng hoạt động
                }
            }

            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
