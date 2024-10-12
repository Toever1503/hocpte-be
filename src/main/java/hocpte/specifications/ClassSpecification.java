package hocpte.specifications;

import hocpte.entities.ClassEntity;
import hocpte.entities.ClassEntity_;
import hocpte.entities.CoursesEntity_;
import hocpte.models.ClassFilterModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassSpecification {
    public static Specification<ClassEntity> byCourse(Long courseId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ClassEntity_.COURSE).get(CoursesEntity_.ID), courseId);
    }

    public static Specification<ClassEntity> like(String q) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(ClassEntity_.TITLE), q);
    }

    public static Specification<ClassEntity> dateBetween(Date from, Date to) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(ClassEntity_.CREATED_DATE), from, to);
    }

    private static Specification<ClassEntity> byStatusIn(List<String> status) {
        return (root, query, criteriaBuilder) -> root.get(ClassEntity_.STATUS).in(status);
    }

    public static Specification<ClassEntity> filter(ClassFilterModel model) {
        List<Specification<ClassEntity>> specs = new ArrayList<>();

        specs.add(byCourse(model.getCourseId()));
        if (model.getQ() != null)
            specs.add(like("%".concat(model.getQ().concat("%"))));

        if (model.getDateFrom() != null && model.getDateTo() != null)
            specs.add(dateBetween(model.getDateFrom(), model.getDateTo()));

        if(model.getStatus() != null)
            specs.add(byStatusIn(model.getStatus()));

        Specification<ClassEntity> finalSpec = null;

        for (Specification<ClassEntity> spec : specs)
            if (finalSpec == null)
                finalSpec = Specification.where(spec);
            else
                finalSpec = finalSpec.and(spec);

        return finalSpec;
    }

}
