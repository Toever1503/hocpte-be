package hocpte.services.impl;

import hocpte.dtos.PostDto;
import hocpte.entities.PostEntity;
import hocpte.models.PostModel;
import hocpte.repositories.IPostRepository;
import hocpte.resources.exception.CustomHandleException;
import hocpte.services.ICategoryService;
import hocpte.services.IPostService;
import hocpte.specifications.PostSpecification;
import hocpte.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class PostServiceImpl implements IPostService {
    //private PostDto mainPostDto;
    private final IPostRepository postRepository;

    private final ICategoryService categoryService;

    public PostServiceImpl(IPostRepository iPostRepository, ICategoryService categoryService) {
        this.postRepository = iPostRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<PostDto> findAll() {
        return this.postRepository.findAll()
                .stream()
                .map(PostDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto> findAll(Pageable page) {
        return this.postRepository.findAll(page)
                .map(PostDto::toDto);
    }

    @Override
    public List<PostDto> findAll(Specification<PostEntity> specs) {
        return null;
    }

    @Override
    public Page<PostDto> filter(Pageable page, Specification<PostEntity> specs) {
        return this.postRepository.findAll(specs, page).map(PostDto::toDto);
    }

    @Override
    public PostDto findById(Long id) {
        return PostDto.toDto(this.getById(id));
    }

    @Override
    public PostEntity getById(Long id) {
        return this.postRepository.findById(id).orElseThrow(() -> new CustomHandleException(1));
    }

    @Override
    public PostDto add(PostModel model) {

        PostEntity postEntity;
        if (model.getId() != null)
            postEntity = this.getById(model.getId());
        else {
            postEntity = PostModel.toEntity(model);
            postEntity.setCreatedBy(SecurityUtils.getCurrentUser().getUser());
        }

        postEntity.setCategory(categoryService.findById(model.getCategory()));
        postEntity.setModifiedBy(SecurityUtils.getCurrentUser().getUser());
        postEntity.setCommentCount(0);

        return PostDto.toDto(this.postRepository.saveAndFlush(postEntity));
    }

    @Override
    public List<PostDto> add(List<PostModel> model) {
        return null;
    }

    @Override
    public PostDto update(PostModel model) {
        return this.add(model);
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) return false;
        try {
            this.postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return false;
        }
        this.postRepository.deleteAllByIdInBatch(ids);
        return true;
    }

    @Override
    public PostEntity findPostEntityByTitle(String title) {
        return this.postRepository.findPostEntitiesByTitle(title);
    }

    @Override
    public Page<PostDto> search(String q, Pageable page) {
        return this.postRepository.findAll(Specification.where(PostSpecification.like(new StringBuilder("%").append(q).append("%").toString())), page).map(PostDto::toDto);
    }

    @Override
    public List<String> getAllPostDate() {
        return this.postRepository.getAllPostDate();
    }

    @Override
    public PostDto[] findNextPrevPosts(Long postId, Long categoryId) {
        Pageable page = PageRequest.of(0, 1);
        List<PostEntity> nextPosts = this.postRepository.findNextPost(postId, categoryId, page);
        List<PostEntity> prevPosts = this.postRepository.findPrevPost(postId, categoryId, page);

        PostDto[] postDtos = new PostDto[2];
        postDtos[0] = prevPosts.size() == 0 ? null : PostDto.toDto(prevPosts.get(0));
        postDtos[1] = nextPosts.size() == 0 ? null : PostDto.toDto(nextPosts.get(0));

        return postDtos;
    }

}
