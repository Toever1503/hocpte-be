package hocpte.repositories;

import hocpte.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long>{
//    CategoryEntity findCategoryEntityByCategory_name(String category_name);
}
