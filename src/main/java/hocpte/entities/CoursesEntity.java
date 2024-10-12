package hocpte.entities;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tbl_courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoursesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content_excerpt")
    private String contentExcerpt;
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private Double price;

    @Column(name = "number_of_enrolled_students")
    private Integer numberOfEnrolledStudents;

    @Column(name = "review")
    private Float review;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @CreatedBy
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "modified_by",nullable = false)
    @LastModifiedBy
    private UserEntity modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date",nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date",nullable = false)
    @UpdateTimestamp
    private Date modifiedDate;

    @Column(name = "lectures")
    private String lectures;

    @Column(name = "img_banner")
    private String imgBanner;

    @Column(name = "total_session")
    private String totalSession;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    private List<TagEntity> tags = new ArrayList<>();
}
