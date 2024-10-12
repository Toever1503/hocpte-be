package hocpte.dtos;

import hocpte.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserMetaDto {
    private Long id;
    private String userName;

    public static UserMetaDto toDto(UserEntity userEntity) {
        if (userEntity == null) return null;
        return UserMetaDto.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .build();
    }
}
