package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.model.Caption;
import aiss.grupo6.videoMiner.repository.CommentRepository;
import aiss.grupo6.videoMiner.repository.VideoRepository;
import aiss.grupo6.videoMiner.exception.CommentNotFoundException;
import aiss.grupo6.videoMiner.exception.VideoNotFoundException;
import aiss.grupo6.videoMiner.model.Comment;
import aiss.grupo6.videoMiner.model.Video;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Comment",
        description = "Integration for an standardized model for comments inside videos in local H2 database"
)
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

    @Operation(
            summary = "Retrieve all Comments",
            description = "Get every Comment object in the database",
            tags = {"comments", "get", "all"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment[].class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/comments")
    public List<Comment> findAll() throws Exception{
        try {
            return repository.findAll();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

    @Operation(
            summary = "Retrieve a Comment by Id",
            description = "Get a Comment object by its id",
            tags = {"comments", "get"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "API could not find data for that id, check format of id"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/comments/{id}")
    public Comment findOne(@Parameter(required = true, description = "Id of the comment to search") @PathVariable String id) throws Exception {
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

    @Operation(
            summary = "Retrieve Comments from a Video",
            description = "Get every Comment object in the database related to a Video",
            tags = {"comments", "get", "video"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment[].class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "API could not find data for that id, check format of id"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/videos/{id}/comments")
    public List<Comment> findCommentsOfVideo(@Parameter(required = true, description = "Id of the video to search for comments") @PathVariable String id) throws Exception {
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
}