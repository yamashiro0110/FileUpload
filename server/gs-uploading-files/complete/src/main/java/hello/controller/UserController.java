package hello.controller;

import hello.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
public class UserController {

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String user(@RequestBody User user) {
        return "posted user:" + user.toString();
    }

}
