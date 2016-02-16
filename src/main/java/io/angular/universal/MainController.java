/**
 * Created by wchegham on 07/02/16.
 */
package io.angular.universal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private TodoService service;

    private Universal universal;

    private ObjectMapper mapper;

    @Autowired
    public MainController(TodoService service) {
        this.service = service;
        this.universal = new Universal();
        this.mapper = new ObjectMapper();
    }

    @RequestMapping("/")
    public String index(Map<String, Object> model) throws Exception {
        List<Todo> todos = service.getTodos();
        String commentBox = universal.render(todos);
        String data = mapper.writeValueAsString(todos);
        model.put("content", commentBox);
        model.put("data", data);
        return "index";
    }
}
