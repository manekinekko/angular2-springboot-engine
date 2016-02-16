/**
 * Created by wchegham on 07/02/16.
 */
package io.angular.universal;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private List<Todo> todos = new ArrayList<>();

    public TodoService() {
        todos.add(new Todo("Peter Parker"));
        todos.add(new Todo("John Doe"));
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public List<Todo> addTodo(Todo todo) {
        todos.add(todo);
        return todos;
    }
}
