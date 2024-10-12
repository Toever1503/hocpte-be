package hocpte.resources;


import hocpte.configs.FrontendConfiguration;
import hocpte.dtos.PostDto;
import hocpte.dtos.ResponseDto;
import hocpte.models.PostFilterModel;
import hocpte.models.PostModel;
import hocpte.services.IPostService;
import hocpte.specifications.PostSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "posts")
public class PostResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IPostService postService;

    public PostResource(IPostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseDto findAll(Pageable page) {
        return ResponseDto.of(this.postService.findAll(page));
    }

    @GetMapping("{id}")
    public ResponseDto findById(@PathVariable("id") Long id) {
        return ResponseDto.of(this.postService.findById(id));
    }

    @GetMapping("get-all-post-date")
    public ResponseDto getAllPostDate() {
        return ResponseDto.of(this.postService.getAllPostDate());
    }

    @PostMapping
    public ResponseDto addPost(@Valid @RequestBody PostModel postModel) {
        postModel.setId(null);
        return ResponseDto.of(this.postService.add(postModel));
    }

    @PutMapping(value = "{id}")
    public ResponseDto updatePost(@PathVariable("id") Long id, @Valid @RequestBody PostModel postModel) {
        postModel.setId(id);
        return ResponseDto.of(this.postService.update(postModel));
    }

    //xóa đối tượng
    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") long id) {
        this.postService.deleteById(id);
    }

    @DeleteMapping(value = "delete")
    public void deleteByIds(@RequestBody List<Long> ids) {
        this.postService.deleteByIds(ids);
    }


    @GetMapping("search")
    public ResponseDto search(@RequestParam("q") String q, Pageable page) {
        return ResponseDto.of(this.postService.search(q, page));
    }

    @PostMapping("filter")
    public ResponseDto filter(Pageable page, @RequestBody PostFilterModel filterModel) {
        return ResponseDto.of(this.postService.filter(page, PostSpecification.filter(filterModel)));
    }

    @GetMapping("find-next-prev-posts/{id}/{categoryId}")
    public ResponseDto findNextPrevPosts(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId) {
        return ResponseDto.of(this.postService.findNextPrevPosts(id, categoryId));
    }
}