package hello.controller;

import hello.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user/file/json")
public class UserFileUploadJsonController {

    @RequestMapping(value = "str", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String uploadByJson(@RequestPart("user") String json, @RequestPart("file") MultipartFile file) {
        return new StringBuilder()
                .append("user:").append(json).append(",")
                .append("fileName:").append(file.getOriginalFilename())
                .toString();
    }

    @RequestMapping(value = "type", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String uploadByJson(@RequestPart("user") User user, @RequestPart("file") MultipartFile file) {
        return new StringBuilder()
                .append("user:").append(user.toString()).append(",")
                .append("fileName:").append(file.getOriginalFilename())
                .toString();
    }

}
