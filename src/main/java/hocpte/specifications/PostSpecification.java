package hocpte.specifications;

import hocpte.entities.PostEntity;
import hocpte.entities.PostEntity_;
import hocpte.models.PostFilterModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {

    public static Specification<PostEntity> like(String q) {
        return (root, query, cb) -> cb.or(
                cb.like(root.get(PostEntity_.TITLE), q),
                cb.like(root.get(PostEntity_.CONTENT), q)
        );
    }

    public static Specification<PostEntity> byCategory(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get(PostEntity_.CATEGORY), categoryId);
    }

    public static Specification<PostEntity> filter(PostFilterModel model) {
        List<Specification> specs = new ArrayList<>();

        if (model.getQ() != null)
            specs.add(like(new StringBuilder("%").append(model.getQ()).append("%").toString()));

        if (model.getCategory() != null)
            specs.add(byCategory(model.getCategory()));


        Specification finalSpec = null;
        for (Specification spec : specs)
            if (finalSpec == null)
                finalSpec = Specification.where(spec);
            else
                finalSpec = finalSpec.and(spec);

        return finalSpec;
    }
}
