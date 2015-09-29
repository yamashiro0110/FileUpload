package hello.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download")
public class FileDownloadController {

    private final String imgPath = "src/main/resources/static/img/seesar.jpg";

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.IMAGE_JPEG_VALUE)
    public Resource download() {
        return new FileSystemResource(imgPath);
    }

}
