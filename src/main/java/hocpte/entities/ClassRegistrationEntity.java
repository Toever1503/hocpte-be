package hocpte.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_class_registration")
public class ClassRegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassEntity classOfCourse;

    @Column(name = "price")
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @UpdateTimestamp
    private Date modifiedDate;

    @ManyToOne
    @JoinColumn
    @CreatedBy
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn
    private UserEntity modifiedBy;

}
