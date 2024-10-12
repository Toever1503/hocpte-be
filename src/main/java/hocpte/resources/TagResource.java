package hocpte.resources;

import hocpte.configs.FrontendConfiguration;
import hocpte.dtos.ResponseDto;
import hocpte.entities.TagEntity;
import hocpte.services.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "tags")
public class TagResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ITagService tagService;

    public TagResource(ITagService tagService) {
        this.tagService = tagService;
    }
    @PostMapping(value = "/add")
    public ResponseEntity<TagEntity> create(TagEntity tagEntity){
        try {
            return ResponseEntity.ok(tagService.create(tagEntity));
        } catch (Exception e){
            return  ResponseEntity.ok().build();
        }
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<TagEntity> update(@PathVariable("id") Long id, TagEntity tagEntity){
        try {
            tagEntity.setId(id);
            return ResponseEntity.ok(tagService.update(tagEntity));
        } catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.ok().build();
        }
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<TagEntity> delete(@PathVariable("id") Long id, TagEntity tagEntity){
        try {
            return ResponseEntity.ok(tagService.delete(tagEntity));
        } catch (Exception e){
            return  ResponseEntity.ok().build();
        }
    }
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<TagEntity> findById(@PathVariable("id") Long id ){
        try {
            TagEntity tagEntity = tagService.findById(id);
            return  ResponseEntity.ok(tagEntity);

        } catch (Exception e){
            return  ResponseEntity.ok().build();
        }
    }

    @GetMapping("search")
    public ResponseDto searchTag(@RequestParam String q){
        return ResponseDto.of(this.tagService.searchTag(q));
    }
}
