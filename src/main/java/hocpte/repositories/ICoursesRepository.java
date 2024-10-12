package hocpte.repositories;

import hocpte.entities.CoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICoursesRepository extends JpaRepository<CoursesEntity, Long>, JpaSpecificationExecutor<CoursesEntity> {
    @Query(value = "SELECT SUBSTRING(created_date, 1,7) as media_date FROM tbl_courses\n" +
            "group by media_date;", nativeQuery = true)
    List<String> findAllCourseDate();
}
