package hocpte.repositories;

import hocpte.entities.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPostRepository extends JpaRepository<PostEntity,Long> , JpaSpecificationExecutor<PostEntity> {
    PostEntity findPostEntitiesByTitle(String title);

    @Query(value = "SELECT SUBSTRING(created_date, 1,7) as media_date FROM tbl_posts\n" +
            "group by media_date;", nativeQuery = true)
    List<String> getAllPostDate();

    @Query("select p from PostEntity p where p.id > ?1 and p.category.id = ?2")
    List<PostEntity> findNextPost(Long postId, Long categoryId, Pageable page);

    @Query("select p from PostEntity p where p.id < ?1 and p.category.id = ?2")
    List<PostEntity> findPrevPost(Long postId, Long categoryId, Pageable page);
}
