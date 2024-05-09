package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.model.Channel;
import aiss.grupo6.videoMiner.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentControllerTest {
    CommentController comment;
    @Test
    public void findAll() throws Exception {
        List<Comment> comments = comment.findAll();
        assertNotNull(comments, "No comments");
        if(!comments.isEmpty()) {
            for (Comment c : comments) {
                assertInstanceOf(String.class, c.getId(), "Comment id must be a string");
                assertInstanceOf(String.class, c.getText(), "Comment text must be a string");
                assertInstanceOf(String.class, c.getCreatedOn(), "Comment creation time must be a string");
                assertInstanceOf(String.class, c.getAuthor(), "Comment author must be a string");
            }
        }
    }

    @Test
    void findOne() throws Exception {
        try {
            Comment c = comment.findOne("1");
        } catch(HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void findCommentsOfVideo() throws Exception{
        try {
            List<Comment> comments = comment.findCommentsOfVideo("1");
            for (Comment c : comments) {
                assertInstanceOf(String.class, c.getId(), "Comment id must be a string");
                assertInstanceOf(String.class, c.getText(), "Comment text must be a string");
                assertInstanceOf(String.class, c.getCreatedOn(), "Comment creation time must be a string");
                assertInstanceOf(String.class, c.getAuthor(), "Comment author must be a string");
            }
        } catch(HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }

    }
}