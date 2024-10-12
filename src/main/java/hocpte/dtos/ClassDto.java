package hocpte.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassDto {

    private Long id;
    private String title;
    private Integer totalStudentNow;
    private Integer maxStudent;
    private Date startDate;
    private Date endDate;
    private String status;
    private String totalSession;
    private Long courseId;

    private UserMetaDto createdBy;
    private UserMetaDto modifiedBy;

    private BaseDto metadata;

    public static ClassDto toDto(hocpte.entities.ClassEntity entity) {
        if (entity == null)
            return null;
        ClassDto dto = new ClassDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setTotalStudentNow(entity.getTotalStudentNow());
        dto.setMaxStudent(entity.getMaxStudent());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(entity.getStatus());
        dto.setTotalSession(entity.getTotalSession());
        dto.setCourseId(entity.getCourse().getId());
        dto.setCreatedBy(UserMetaDto.toDto(entity.getCreatedBy()));
        dto.setModifiedBy(UserMetaDto.toDto(entity.getModifiedBy()));
        dto.setMetadata(BaseDto.toDTO(entity));
        return dto;
    }
}
