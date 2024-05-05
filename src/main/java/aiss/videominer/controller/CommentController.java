package aiss.videominer.controller;

import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.exception.CommentNotFoundException;
import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.CommentRepository;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer")
public class CommentController {

    @Autowired
    private final CommentRepository repository;
    @Autowired
    VideoRepository videoRepository;

    public CommentController(CommentRepository repository) {
        this.repository=repository;
    }

    @GetMapping("/comments")
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @GetMapping("/comments/{id}")
    public Comment findOne(@PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment=repository.findById(id);
        if (!comment.isPresent()) {
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

    @GetMapping("/videos/{id}/comments")
    public List<Comment> findCommentsOfVideo(@PathVariable String id) throws VideoNotFoundException {
        Optional<Video> video = videoRepository.findById(id);
        if (!video.isPresent()) {
            throw new VideoNotFoundException();
        }
        return video.get().getComments();
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping()
//    public Comment create(@Valid @RequestBody Comment com) {
//        Comment newC = repository.save(new Comment(com.getText(),com.getCreatedOn(),com.getAuthor()));
//        return newC;
//    }


}