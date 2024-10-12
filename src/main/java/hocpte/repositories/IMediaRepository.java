package hocpte.repositories;

import hocpte.entities.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMediaRepository extends JpaRepository<MediaEntity, Long>, JpaSpecificationExecutor<MediaEntity> {
    @Query("SELECT DISTINCT m.fileType FROM MediaEntity m")
    List<String> findAllMediaType();

    @Query(value = "SELECT SUBSTRING(created_date, 1,7) as media_date FROM tbl_medias\n" +
            "group by media_date;", nativeQuery = true)
    List<String> findAllMediaDate();
}
