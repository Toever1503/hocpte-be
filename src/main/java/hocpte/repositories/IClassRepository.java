package hocpte.repositories;

import hocpte.entities.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClassRepository extends JpaRepository<ClassEntity, Long>, JpaSpecificationExecutor<ClassEntity> {
    @Modifying
    @Query("delete from ClassEntity c where c.course.id in ?1")
    void deleteAllByCourseIdIn(List<Long> ids);
}
