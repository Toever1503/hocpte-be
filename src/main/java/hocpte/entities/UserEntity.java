package hocpte.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tbl_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "sex")
    private String sex;
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "status")
    private Boolean status = true;
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "address")
    private String address;
    public static final String FOLDER = "user/";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @UpdateTimestamp
    private Date modifiedDate;

    public static String USER_NO_AVATAR = "https://team-2.s3.ap-northeast-2.amazonaws.com/user/no-avatar.png";

    @Override
    public String toString() {
        return "user entity to string";
    }

    public static void main(String[] args) {

    }
}
