package hocpte.resources;

import hocpte.utils.FileUploadProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/test")
public class TestResouce {
    private final FileUploadProvider fileUploadProvider;

    public TestResouce(FileUploadProvider fileUploadProvider) {
        this.fileUploadProvider = fileUploadProvider;
    }


    @PostMapping("/upload")
    public void upload(MultipartFile file) throws IOException {
        fileUploadProvider.uploadFile(file, "admin/");
    }
}
