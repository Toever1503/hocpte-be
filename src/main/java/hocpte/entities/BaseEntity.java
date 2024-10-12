package hocpte.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
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
    @JoinColumn(nullable = false)
    @LastModifiedBy
    private UserEntity modifiedBy;
}
