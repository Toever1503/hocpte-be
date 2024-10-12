package hocpte.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordModel {

    @NotNull
    private String currentPasswd;

    @NotNull
    @Length(min = 6, max = 12)
    private String newPasswd;
}
