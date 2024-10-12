package hocpte.dtos;

import hocpte.entities.CoursesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseMetaDto {
    private Long id;
    private String title;

    public static CourseMetaDto toDto(CoursesEntity entity) {
        if (entity == null) return null;

        return CourseMetaDto
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }
}
