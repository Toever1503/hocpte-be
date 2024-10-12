package hocpte.models;

import hocpte.entities.ClassEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClassModel {
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Integer totalStudentNow;

    @NotNull
    private Integer maxStudent;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Long courseId;

    public static ClassEntity toEntity(ClassModel model){
        if (model == null) return null;
        ClassEntity entity = new ClassEntity();
        entity.setId(model.getId());
        entity.setTitle(model.getTitle());
        entity.setTotalStudentNow(model.getTotalStudentNow());
        entity.setMaxStudent(model.getMaxStudent());
        entity.setStartDate(model.getStartDate());
        entity.setEndDate(model.getEndDate());
        return entity;
    }
}
