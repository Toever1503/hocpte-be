package hocpte.dtos;

import hocpte.entities.CoursesEntity;
import hocpte.entities.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoursesDto {
    private Long id;
    private String title;
    private String contentExcerpt;
    private String description;
    private String status;
    private Double price;
    private UserMetaDto createdBy;
    private UserMetaDto modifiedBy;
    private Date createdDate;
    private Date modifiedDate;
    private String lectures;
    private String imgBanner;
    private String totalSession;
    private Integer numberOfEnrolledStudents = 0;
    private Float review = 0f;
    private List<String> tags;

    public static CoursesDto toDto(CoursesEntity entity) {
        if (entity == null) return null;
        return CoursesDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .createdBy(UserMetaDto.toDto(entity.getCreatedBy()))
                .modifiedBy(UserMetaDto.toDto(entity.getModifiedBy()))
                .createdDate(entity.getCreatedDate())
                .modifiedDate(entity.getModifiedDate())
                .lectures(entity.getLectures())
                .imgBanner(entity.getImgBanner())
                .status(entity.getStatus())
                .totalSession(entity.getTotalSession())
                .numberOfEnrolledStudents(entity.getNumberOfEnrolledStudents())
                .review(entity.getReview())
                .contentExcerpt(entity.getContentExcerpt())
                .tags(entity.getTags() != null ? entity.getTags().stream().map(TagEntity::getTagName).collect(Collectors.toList()) : null)
                .build();
    }
}
