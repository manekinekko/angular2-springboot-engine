package io.angular.universal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class UniversalTest {

    @Test
    public void testRenderCommentBox() throws Exception {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo("Peter Parker"));
        todos.add(new Todo("John Doe"));

        Universal universal = new Universal();
        String html = universal.render(todos);

        assertThat(html, startsWith("<"));

        //Document doc = Jsoup.parse(html);
        //assertThat(doc.select("div.comment").size(), is(2));
    }

}