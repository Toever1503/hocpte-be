package hocpte.dtos;

import hocpte.entities.ClassRegistrationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClassRegistrationDto {
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String status;

    private ClassDto classOfCourse;

    private CourseMetaDto course;

    private Double price;

    private BaseDto metadata;

    public static ClassRegistrationDto toDto(ClassRegistrationEntity entity) {
        if (entity == null) return null;

        return ClassRegistrationDto
                .builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .status(entity.getStatus())
                .price(entity.getPrice())
                .metadata(BaseDto
                        .builder()
                        .createdDate(entity.getCreatedDate())
                        .modifiedDate(entity.getModifiedDate())
                        .createdBy(UserMetaDto.toDto(entity.getCreatedBy()))
                        .updatedBy(UserMetaDto.toDto(entity.getModifiedBy()))
                        .build())
                .classOfCourse(ClassDto.toDto(entity.getClassOfCourse()))
                .course(CourseMetaDto.toDto(entity.getClassOfCourse().getCourse()))
                .build();
    }
}
