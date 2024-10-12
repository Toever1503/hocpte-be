package hocpte.dtos;

import hocpte.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private Long id;
    private String userName;
    private String email;
    private String fullName;
    private String avatar;
    private String phone;
    private String sex;
    private Date birthDate;
    private boolean status;
    private String address;
    private RoleDto role;
    private BaseDto metadata;
    private Date createdDate;
    private Date modifiedDate;

    public static UserProfileDto toDto(UserEntity userEntity) {
        if (userEntity == null) return null;
        return UserProfileDto.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .sex(userEntity.getSex())
                .fullName(userEntity.getFullName())
                .avatar(userEntity.getAvatar() == null ? UserEntity.USER_NO_AVATAR : userEntity.getAvatar())
                .birthDate(userEntity.getBirthDate())
                .status(userEntity.getStatus())
                .address(userEntity.getAddress())
                .role(RoleDto.builder()
                        .id(userEntity.getRoleEntity().getId())
                        .roleName(userEntity.getRoleEntity().getRoleName())
                        .build())
                .createdDate(userEntity.getCreatedDate())
                .modifiedDate(userEntity.getModifiedDate())
                .build();
    }

}

