package hocpte.services.impl;

import hocpte.dtos.MediaDto;
import hocpte.entities.MediaEntity;
import hocpte.models.MediaModel;
import hocpte.repositories.IMediaRepository;
import hocpte.resources.exception.CustomHandleException;
import hocpte.services.IMediaService;
import hocpte.utils.FileUploadProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MediaServiceImpl implements IMediaService {
    private final IMediaRepository mediaRepository;
    private final FileUploadProvider fileUploadProvider;

    public MediaServiceImpl(IMediaRepository mediaRepository, FileUploadProvider fileUploadProvider) {
        this.mediaRepository = mediaRepository;
        this.fileUploadProvider = fileUploadProvider;
    }

    @Override
    public List<MediaDto> findAll() {
        return null;
    }

    @Override
    public Page<MediaDto> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<MediaDto> findAll(Specification<MediaEntity> specs) {
        return null;
    }

    @Override
    public Page<MediaDto> filter(Pageable page, Specification<MediaEntity> specs) {
        return this.mediaRepository.findAll(specs, page).map(MediaDto::toDto);
    }

    @Override
    public MediaDto findById(Long id) {
        return null;
    }

    @Override
    public MediaEntity getById(Long id) {
        return null;
    }

    @Override
    public MediaDto add(MediaModel model) {
        MediaEntity mediaEntity = this.fileUploadProvider.uploadFile(model.getFile());
        return MediaDto.toDto(this.mediaRepository.saveAndFlush(mediaEntity));
    }

    @Override
    public List<MediaDto> add(List<MediaModel> model) {
        return this.mediaRepository.saveAllAndFlush(model
                        .stream()
                        .map(m -> this.fileUploadProvider.uploadFile(m.getFile()))
                        .collect(Collectors.toList()))
                .stream()
                .map(MediaDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MediaDto update(MediaModel model) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            this.mediaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return false;
    }

    @Override
    public List<String> getAllMediaType() {
        return this.mediaRepository.findAllMediaType();
    }

    @Override
    public List<String> getAllMediaDate() {
        return this.mediaRepository.findAllMediaDate();
    }
}
