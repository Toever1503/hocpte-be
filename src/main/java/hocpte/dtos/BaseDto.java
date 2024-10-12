package hocpte.dtos;

import hocpte.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseDto {
    private Date createdDate;

    private Date modifiedDate;

    private UserMetaDto createdBy;

    private UserMetaDto updatedBy;

    public static BaseDto toDTO(BaseEntity baseEntity) {
        if (baseEntity == null) return null;
        return BaseDto.builder()
                .createdDate(baseEntity.getCreatedDate())
                .modifiedDate(baseEntity.getModifiedDate())
                .createdBy(UserMetaDto.toDto(baseEntity.getCreatedBy()))
                .updatedBy(UserMetaDto.toDto(baseEntity.getModifiedBy()))
                .build();
    }

}
