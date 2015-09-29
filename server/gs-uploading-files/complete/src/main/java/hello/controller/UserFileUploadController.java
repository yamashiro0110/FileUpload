package hello.controller;

import hello.domain.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user/file")
public class UserFileUploadController {

    @ModelAttribute("user")
    private User prototype() {
        return new User();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String uploadWithUser() {
        return "user/upload";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody String uploadWithUser(
            @RequestPart("user") User user,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return new StringBuilder()
                .append("user:").append(user.toString()).append(",")
                .append("fileName:").append(file.getOriginalFilename())
                .toString();
    }

}
