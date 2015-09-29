package hello.controller;

import hello.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/file/json")
public class UserFileUploadJsonController {

    @RequestMapping(value = "str", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadByJson(@RequestPart("user") String json, @RequestPart("file") MultipartFile file) {
        return new StringBuilder()
                .append("user:").append(json).append(",")
                .append("fileName:").append(file.getOriginalFilename())
                .toString();
    }

    @RequestMapping(value = "type", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadByJson(@RequestPart("user") User user, @RequestPart("file") MultipartFile file) {
        return new StringBuilder()
                .append("user:").append(user.toString()).append(",")
                .append("fileName:").append(file.getOriginalFilename())
                .toString();
    }

    @RequestMapping(value = "files", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadUserAndFiles(@RequestPart("user") User user, @RequestPart("file") MultipartFile[] files) {
        return new StringBuilder()
                .append("user:").append(user.toString()).append(",")
                .append("file:").append(
                        Arrays.asList(files)
                                .stream()
                                .map(e -> e.getOriginalFilename())
                                .collect(Collectors.toList())
                                .toString()
                )
                .toString();
    }

}
