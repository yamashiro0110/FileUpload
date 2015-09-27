package hello.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import hello.service.FileSaveService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/upload")
@SessionAttributes("user")
public class FileUploadController {

    @Resource
    private FileSaveService fileSaveService;

    @RequestMapping(method = RequestMethod.GET)
    public String provideUploadInfo() {
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("msg", "You failed to upload " + name + " because the file was empty.");
            return "error";
        }

        if (!fileSaveService.save(name, file)) {
            model.addAttribute("msg", "You failed to upload " + name);
            return "error";
        }

        model.addAttribute("imgPath", "/img/" + name);
        return "img";
    }

}
