package hocpte.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserRegister {
    @ApiModelProperty(notes = "User user name", dataType = "String", example = "user")
    private String userName;

    @ApiModelProperty(notes = "User full name", dataType = "String", example = "Nguyen Van A")
    private String fullName;

    @ApiModelProperty(notes = "User Email", dataType = "String", example = "email@gmail.com")
    private String email;

    @ApiModelProperty(notes = "User password", dataType = "String", example = "123456")
    private String password;

}
