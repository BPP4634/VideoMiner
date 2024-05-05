package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.repository.CommentRepository;
import aiss.grupo6.videoMiner.repository.VideoRepository;
import aiss.grupo6.videoMiner.exception.CommentNotFoundException;
import aiss.grupo6.videoMiner.exception.VideoNotFoundException;
import aiss.grupo6.videoMiner.model.Comment;
import aiss.grupo6.videoMiner.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer")
public class CommentController {

    @Autowired
    CommentRepository repository;

    @Autowired
    VideoRepository videoRepository;

    @Value( "${message.commentNotFound}" )
    private String commentError;

    @Value( "${message.videoNotFound}" )
    private String videoError;

    @Value( "${message.internalError}" )
    private String internalError;

    @GetMapping("/comments")
    public List<Comment> findAll() throws Exception{
        try {
            return repository.findAll();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

    @GetMapping("/comments/{id}")
    public Comment findOne(@PathVariable String id) throws Exception {
        try {
            Optional<Comment> comment = repository.findById(id);
            if (!comment.isPresent()) {
                throw new CommentNotFoundException(commentError);
            }
            return comment.get();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

    @GetMapping("/videos/{id}/comments")
    public List<Comment> findCommentsOfVideo(@PathVariable String id) throws Exception {
        try {
            Optional<Video> video = videoRepository.findById(id);
            if (!video.isPresent()) {
                throw new VideoNotFoundException(videoError);
            }
            return video.get().getComments();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping()
//    public Comment create(@Valid @RequestBody Comment com) {
//        Comment newC = repository.save(new Comment(com.getText(),com.getCreatedOn(),com.getAuthor()));
//        return newC;
//    }


}