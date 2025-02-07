package hocpte.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserFilterModel {
    @ApiModelProperty(notes = "User user name", dataType = "String", example = "Anv")
    private String userName;

    @ApiModelProperty(notes = "User full name", dataType = "String", example = "Nguyen Van A")
    private String fullName;

    @ApiModelProperty(notes = "User birthdate", dataType = "Date", example = "2002-04-29")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @ApiModelProperty(notes = " phone, phone must format follow vietnam", dataType = "String", example = "0952888888")
    private String phone;

    @ApiModelProperty(notes = "User Email", dataType = "String", example = "email@gmail.com")
    private String email;

    @ApiModelProperty(notes = "sex", dataType = "String", example = "1 in (nam, nữ, khác)")
    private String sex;

    @ApiModelProperty(notes = "address", dataType = "String", example = "my dinh")
    private String address;

    @ApiModelProperty(notes = "User createdDate", dataType = "Date", example = "2002-04-29")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @ApiModelProperty(notes = "User modifiedDate", dataType = "Date", example = "2002-04-29")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date modifiedDate;
}
