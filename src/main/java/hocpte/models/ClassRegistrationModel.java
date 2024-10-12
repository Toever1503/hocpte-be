package hocpte.models;

import hocpte.entities.ClassEntity;
import hocpte.entities.ClassRegistrationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClassRegistrationModel {

    private Long classOfCourse;
    private String fullName;
    private String email;
    private String phone;

    public static ClassRegistrationEntity toEntity(ClassRegistrationModel model){
        if(model == null) return null;

        return ClassRegistrationEntity
                .builder()
                .fullName(model.getFullName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .build();
    }
}
