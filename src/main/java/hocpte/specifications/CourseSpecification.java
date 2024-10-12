package hocpte.specifications;

import hocpte.entities.CoursesEntity;
import hocpte.entities.CoursesEntity_;
import hocpte.models.CourseFilterModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseSpecification {
    public static Specification<CoursesEntity> like(String q) {
        String finalQ = "%" + q + "%";
        return (root, query, cb) -> cb.or(
                cb.like(root.get(CoursesEntity_.TITLE), finalQ),
                cb.like(root.get(CoursesEntity_.DESCRIPTION), finalQ)
        );
    }

    public static Specification<CoursesEntity> betweenDate(Date from, Date to) {
        return (root, query, cb) -> cb.between(root.get(CoursesEntity_.CREATED_DATE), from, to);
    }

    public static Specification<CoursesEntity> filter(CourseFilterModel model) {
        List<Specification> specs = new ArrayList<>();
        if (model.getQ() != null)
            specs.add(like(model.getQ()));

        if (model.getCreatedDateFrom() != null && model.getCreatedDateTo() != null)
            specs.add(betweenDate(model.getCreatedDateFrom(), model.getCreatedDateTo()));

        Specification<CoursesEntity> finalSpec = null;
        for (Specification spec : specs) {
            if (finalSpec == null) {
                finalSpec = Specification.where(spec);
            } else {
                finalSpec = finalSpec.and(spec);
            }
        }
        return finalSpec;
    }
}
