package hocpte.resources;

import hocpte.configs.FrontendConfiguration;
import hocpte.entities.CategoryEntity;
import hocpte.services.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "categories")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ICategoryService categoryService;

    public CategoryResource(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("all")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
    @GetMapping
    public ResponseEntity<Page<CategoryEntity>> findAllCategories(Pageable page) {
        return ResponseEntity.ok(categoryService.findAll(page));
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> create(@Valid @RequestBody CategoryEntity categoryEntity) {
        return ResponseEntity.ok(categoryService.create(categoryEntity));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<CategoryEntity> update(@PathVariable("id") Long id,@Valid @RequestBody CategoryEntity categoryEntity) {
        categoryEntity.setId(id);
        return ResponseEntity.ok(categoryService.update(categoryEntity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.deleteById(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryEntity> findById(@PathVariable("id") Long id) {
        CategoryEntity categoryEntity = categoryService.findById(id);
        return ResponseEntity.ok(categoryEntity);
    }

}
