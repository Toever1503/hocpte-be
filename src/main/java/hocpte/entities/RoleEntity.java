package hocpte.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEntity {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String TEACHER = "ROLE_TEACHER";
    public static final String EMPLOYEE = "ROLE_EMP";
    public static final String USER = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "role_name", unique = true)
    private String roleName;
}
