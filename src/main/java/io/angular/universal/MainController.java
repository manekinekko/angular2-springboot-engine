/**
 * Created by wchegham on 07/02/16.
 */
package io.angular.universal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    public MainController() {}

    @RequestMapping("/")
    public String index() throws Exception {
        return "index";
    }
}
