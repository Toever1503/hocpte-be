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
@Table(name = "tbl_posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "content", columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String contentExcerpt;

    @Column(name = "featured_image")
    private String featuredImage;

    @Column
    private Integer commentCount;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "modified_by")
    private UserEntity modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(nullable = false)
    private Date modifiedDate;

    @ManyToOne
    private CategoryEntity category;

    @Override
    public String toString() {
        return "PostsEntity{} to string";
    }

    public static void main(String[] args) {

    }

}
