package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user/file/json")
public class UserFileUploadJsonController {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private User convert(String json) {
        try {
            return MAPPER.readValue(json, User.class);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new User();
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody String uploadByJson(@RequestPart("user") String json, @RequestPart("file") MultipartFile file) {
        User user = convert(json);

        return new StringBuilder()
                .append("user:").append(user.toString()).append(",")
                .append("fileName:").append(file.getOriginalFilename())
                .toString();
    }

}
