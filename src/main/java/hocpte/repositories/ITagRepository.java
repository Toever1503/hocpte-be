package hocpte.repositories;

import hocpte.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITagRepository extends JpaRepository<TagEntity, Long>, JpaSpecificationExecutor<TagEntity> {
    Optional<TagEntity> findById(Long id);

    @Query("select t.tagName from TagEntity t where t.tagName like %?1%")
    List<String> searchTag(String q);

    List<TagEntity> findAllByTagNameIn(List<String> tags);
}
