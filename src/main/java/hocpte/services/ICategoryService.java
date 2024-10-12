package hocpte.services;

import hocpte.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ICategoryService {
//  public List<CategoryEntity> findByApproximateName(String input);
    public CategoryEntity create(CategoryEntity categoryEntity);
    public CategoryEntity update(CategoryEntity categoryEntity);
    public  CategoryEntity delete(CategoryEntity categoryEntity);
    public CategoryEntity findById(Long id);
    List<CategoryEntity> findAll();
    Page<CategoryEntity> findAll(Pageable page);


    boolean deleteById(Long id);
}
