package hocpte.specifications;

import hocpte.entities.MediaEntity;
import hocpte.entities.MediaEntity_;
import hocpte.entities.UserEntity;
import hocpte.entities.UserEntity_;
import hocpte.models.MediaFilterModel;
import hocpte.utils.SecurityUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;

public class MediaSpecification {
    public static Specification<MediaEntity> likeFileName(String fileName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(MediaEntity_.FILE_NAME), new StringBuilder().append("%").append(fileName).append("%").toString());
    }

    public static Specification<MediaEntity> byOrFileType(List<String> fileType) {
        return (root, query, criteriaBuilder) -> root.get(MediaEntity_.FILE_TYPE).in(fileType);
    }

    public static Specification<MediaEntity> byCreatedBy(Long createdBy) {
        return (root, query, criteriaBuilder) -> {
            Join<MediaEntity, UserEntity> join = root.join(MediaEntity_.CREATED_BY);
            return criteriaBuilder.equal(join.get(UserEntity_.ID), createdBy);
        };
    }

    public static Specification<MediaEntity> filter(MediaFilterModel filterModel) {
        List<Specification> specs = new ArrayList<>();
        specs.add(byCreatedBy(SecurityUtils.getCurrentUserId()));

        if (filterModel.getQ() != null) {
            specs.add(MediaSpecification.likeFileName(filterModel.getQ()));
        }
        if (filterModel.getFileType() != null) {
            specs.add(MediaSpecification.byOrFileType(filterModel.getFileType()));
        }

        Specification<MediaEntity> finalSpec = null;
        for (Specification<MediaEntity> spec : specs) {
            if (finalSpec == null) {
                finalSpec = Specification.where(spec);
            } else {
                finalSpec = finalSpec.and(spec);
            }
        }
        return finalSpec;
    }

}
