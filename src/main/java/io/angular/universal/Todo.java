/**
 * Created by wchegham on 07/02/16.
 */
package io.angular.universal;

public class Todo {
    private String text;

    public Todo(){}

    public Todo(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {

        return text;
    }
}
