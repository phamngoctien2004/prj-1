package com.web.prj.Helpers;

import com.web.prj.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserHelper {
    public static Specification<User> containField(String nameField, String filter){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(nameField), "%" + filter + "%");
        };
    }
}
