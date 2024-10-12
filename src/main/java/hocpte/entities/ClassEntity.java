package hocpte.entities;


import hocpte.enums.EClassStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_classes")
public class ClassEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "class_name")
    private String title;

    @Column(name = "total_student_now")
    private Integer totalStudentNow = 0;

    @Column(name = "max_student")
    private Integer maxStudent;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "status")
    private String status = EClassStatus.NOT_START.name();

    @Column(name = "total_session")
    private String totalSession;

    @ManyToOne
    private CoursesEntity course;

}
