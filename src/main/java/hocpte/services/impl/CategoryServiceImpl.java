package hocpte.services.impl;

import hocpte.entities.CategoryEntity;
import hocpte.repositories.ICategoryRepository;
import hocpte.resources.exception.CustomHandleException;
import hocpte.services.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository iCategoryRepository;

    public CategoryServiceImpl(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

    @Override
    public List<CategoryEntity> findAll() {
        return iCategoryRepository.findAll();
    }

    @Override
    public Page<CategoryEntity> findAll(Pageable page) {
        return this.iCategoryRepository.findAll(page);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            iCategoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }

    @Override
    public CategoryEntity findById(Long id) {
        return iCategoryRepository.findById(id).orElseThrow(() -> new CustomHandleException(1));
    }

    @Override
    public CategoryEntity create(CategoryEntity categoryEntity) {
        try {
            return iCategoryRepository.saveAndFlush(categoryEntity);
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }

    @Override
    public CategoryEntity update(CategoryEntity categoryEntity) {
        try {
            return iCategoryRepository.saveAndFlush(categoryEntity);
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }

    @Override
    public CategoryEntity delete(CategoryEntity categoryEntity) {
        iCategoryRepository.delete(categoryEntity);
        return categoryEntity;
    }
}


//
//    @Override
//    public List<CategoryDto> findAll() {
////        return this.iCategoryRepository.findAll()
//        return this.iCategoryRepository.findAll();
////                .stream()
////                .map(CategoryDto::toDto)
////                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Page<CategoryDto> findAll(Pageable page) {
//        return null;
//    }
//
//    @Override
//    public List<CategoryDto> findAll(Specification<CategoryEntity> specs) {
//        return null;
//    }
//
//    @Override
//    public Page<CategoryDto> filter(Pageable page, Specification<CategoryEntity> specs) {
//        return null;
//    }
//
//    @Override
//    public CategoryDto findById(Long id) {
//        if(id == null) return null;
//        try {
//            CategoryEntity categoryEntity = this.iCategoryRepository.findById(id).get();
//            return CategoryDto.toDto(categoryEntity);
//        } catch (Exception){
//            return null;
//        }
//    }
//
//    @Override
//    public CategoryEntity getById(Long id) {
//        return this.iCategoryRepository.findById(id).get();
//    }
//
//    @Override
//    public CategoryDto add(CategoryModel model) {
//        if (model == null) return null;
//        try {
//            return CategoryDto.toDto(this.iCategoryRepository.save(CategoryModel.toEntity(model)));
//        } catch (Exception e){
//            return null;
//        }
//    }
//
//    @Override
//    public List<CategoryDto> add(List<CategoryModel> model) {
//        return null;
//    }
//
//    @Override
//    public CategoryDto update(CategoryModel model) {
//        if (this.iCategoryRepository.findById(model.getId().isPresent())){
//            return CategoryDto.toDto(this.iCategoryRepository.save(CategoryModel.toEntity(model)));
//        }
//        return null;
//    }
//
//    @Override
//    public boolean deleteById(Long id) {
//        if (id == null) return false;
//        try {
//            this.iCategoryRepository.deleteById(id);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean deleteByIds(List<Long> ids) {
//        if (ids.isEmpty()){
//            return false;
//        }
//        this.iCategoryRepository.deleteAllByIdInBatch(ids);
//        return true;
//    }
//
//    @Override
//    public CategoryEntity findCategoryEntityByCategory_name(String category_name) {
//        return this.iCategoryRepository.findCategoryEntityByCategory_name(category_name);
//    }



