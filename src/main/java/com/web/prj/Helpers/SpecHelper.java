package com.web.prj.Helpers;

import org.springframework.data.jpa.domain.Specification;

public class SpecHelper {
    public static <T> Specification<T> containField(String nameField, String filter) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(nameField)),
                    "%" + filter.toLowerCase() + "%"
            );
        });
    }
    public static <T> Specification<T> equal(String nameField, Number value) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(nameField), value);
        });
    }
//    public static <T> Specification<T> NotDeleted(){
//        return ((root, query, criteriaBuilder) -> {
//            return criteriaBuilder.isNull(root.get("deletedAt"));
//        });
//    }
}
