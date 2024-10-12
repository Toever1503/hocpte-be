package hocpte.services.impl;

import hocpte.dtos.TagDto;
import hocpte.entities.TagEntity;
import hocpte.repositories.ITagRepository;
import hocpte.services.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class TagServiceImpl implements ITagService {
    private final ITagRepository iTagRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TagServiceImpl(ITagRepository iTagRepository) {
        this.iTagRepository = iTagRepository;
    }

    @Override
    public TagEntity create(TagEntity tagEntity) {
//        TagEntity tagEntity1 = new TagEntity();
//        tagEntity1.setId(tagEntity.getId());
//        tagEntity1.setTags_name(tagEntity.getTags_name());
//
//        iTagRepository.save(tagEntity1);
//        return TagEntity.builder()
//                .id(tagEntity.getId())
//                .tags_name(tagEntity.getTags_name())
//                .build();
        return iTagRepository.save(tagEntity);
    }

    @Override
    public synchronized TagEntity update(TagEntity tagEntity) {
        return iTagRepository.save(tagEntity);
    }

    @Override
    public TagEntity delete(TagEntity tagEntity) {
        iTagRepository.delete(tagEntity);
        return tagEntity;
    }

    @Override
    public TagEntity findById(Long id) {
        return iTagRepository.findById(id).get();
    }

    @Override
    public synchronized List<TagEntity> findAll() {
        return iTagRepository.findAll();
    }

    @Override
    public List<String> searchTag(String q) {
        return this.iTagRepository.searchTag(q);
    }
}
