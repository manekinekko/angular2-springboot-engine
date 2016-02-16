/**
 * Created by wchegham on 07/02/16.
 */
package io.angular.universal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/todos.json")
public class TodoController {

    private TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> getTodos() {
        return service.getTodos();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Todo> addTodo(Todo todo) {
        return service.addTodo(todo);
    }
}
