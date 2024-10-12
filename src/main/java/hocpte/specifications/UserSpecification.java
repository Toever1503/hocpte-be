package hocpte.specifications;

import hocpte.entities.UserEntity;
import hocpte.entities.UserEntity_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> byId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(UserEntity_.ID), id);
    }

}
