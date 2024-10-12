package hocpte.resources;

import hocpte.configs.FrontendConfiguration;
import hocpte.dtos.ResponseDto;
import hocpte.models.MediaFilterModel;
import hocpte.models.MediaModel;
import hocpte.services.IMediaService;
import hocpte.specifications.MediaSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "medias")
public class MediaResource {

    private final IMediaService mediaService;

    public MediaResource(IMediaService mediaService) {
        this.mediaService = mediaService;
    }


    @GetMapping("get-all-media-type")
    public ResponseDto getAllMediaType() {
        return ResponseDto.of(this.mediaService.getAllMediaType());
    }

    @GetMapping("get-all-media-date")
    public ResponseDto getAllMediaDate() {
        return ResponseDto.of(this.mediaService.getAllMediaDate());
    }
    @PostMapping("filter")
    public ResponseDto filter(@RequestBody MediaFilterModel filterModel, Pageable page) {
        return ResponseDto.of(this.mediaService.filter(page, MediaSpecification.filter(filterModel)));
    }

    @PostMapping
    public ResponseDto uploadFile(@Valid MediaModel model) {
        return ResponseDto.of(this.mediaService.add(model));
    }

    @PostMapping("bulk-upload")
    public ResponseDto uploadFiles(List<MultipartFile> files) {
        return ResponseDto.of(this.mediaService.add(files.stream().map(MediaModel::new).collect(Collectors.toList())));
    }

    @DeleteMapping("{id}")
    public ResponseDto deleteById(@PathVariable Long id) {
        return ResponseDto.of(this.mediaService.deleteById(id));
    }
}
