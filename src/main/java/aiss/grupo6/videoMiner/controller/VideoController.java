package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.ChannelNotFoundException;
import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.model.Channel;
import aiss.grupo6.videoMiner.model.Comment;
import aiss.grupo6.videoMiner.repository.ChannelRepository;
import aiss.grupo6.videoMiner.repository.VideoRepository;
import aiss.grupo6.videoMiner.exception.VideoNotFoundException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Video",
        description = "Integration for an standardized model for videos in local H2 database"
)
@RestController
@RequestMapping("/videominer")
public class VideoController {
    @Autowired
    VideoRepository repository;

    @Autowired
    ChannelRepository channelRepository;

    @Value( "${message.videoNotFound}" )
    private String videoError;

    @Value( "${message.internalError}" )
    private String internalError;

    @Value( "${message.channelNotFound}" )
    private String channelError;

    @Operation(
            summary = "Retrieve all Videos",
            description = "Get every Video object in the database",
            tags = {"comments", "get", "all"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Video[].class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/videos")
    public List<Video> findAll() throws Exception{
        try{
            return repository.findAll();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }

    }

    @Operation(
            summary = "Retrieve a Video by Id",
            description = "Get a Video object by its id",
            tags = {"videos", "get"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Video.class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "API could not find data for that id, check format of id"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/videos/{id}")
    public Video findOne(@Parameter(required = true, description = "Id of the video to search") @PathVariable String id) throws Exception {
        try {
            Optional<Video> video = repository.findById(id);
            if (!video.isPresent()) {
                throw new VideoNotFoundException(videoError);
            }
            return video.get();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

    @Operation(
            summary = "Retrieve Videos from a Channel",
            description = "Get every Video object in the database related to a Channel",
            tags = {"videos", "get", "channel"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Video[].class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "API could not find data for that id, check format of id"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })

    @GetMapping("/channels/{id}/videos")
    public List<Video> findVideosOfChannel(@Parameter(required = true, description = "Id of the channel to search for videos") @PathVariable String id) throws Exception {
        try {
            Optional<Channel> channel = channelRepository.findById(id);
            if (!channel.isPresent()) {
                throw new ChannelNotFoundException(channelError);
            }
            return channel.get().getVideos();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

}
