package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.model.Channel;
import aiss.grupo6.videoMiner.repository.CaptionRepository;
import aiss.grupo6.videoMiner.repository.VideoRepository;
import aiss.grupo6.videoMiner.exception.CaptionNotFoundException;
import aiss.grupo6.videoMiner.exception.VideoNotFoundException;
import aiss.grupo6.videoMiner.model.Caption;
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
        name = "Caption",
        description = "Integration for an standardized model for captions inside videos in local H2 database"
)
@RestController
@RequestMapping("/videominer")
public class CaptionController {

    @Autowired
    CaptionRepository repository;

    @Autowired
    VideoRepository videoRepository;

    @Value( "${message.captionNotFound}" )
    private String captionError;

    @Value( "${message.internalError}" )
    private String internalError;

    @Value( "${message.videoNotFound}" )
    private String videoError;

    @Operation(
            summary = "Retrieve all Captions",
            description = "Get every Caption object in the database",
            tags = {"captions", "get", "all"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Caption[].class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/captions")
    public List<Caption> findAll() throws Exception{
        try{
            return repository.findAll();
        } catch(RuntimeException e) {
            throw new InternalErrorException(internalError);
        }

    }

    @Operation(
            summary = "Retrieve a Caption by Id",
            description = "Get a Caption object by its id",
            tags = {"captions", "get"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Caption.class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "API could not find data for that id, check format of id"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/captions/{id}")
    public Caption findOne(@Parameter(required = true, description = "Id of the caption to search") @PathVariable String id) throws Exception {
        try{
            Optional<Caption> caption=repository.findById(id);
            if (!caption.isPresent()) {
                throw new CaptionNotFoundException(captionError);
            }
            return caption.get();
        } catch(RuntimeException e){
            throw new InternalErrorException(internalError);
        }

    }

    @Operation(
            summary = "Retrieve Captions from a Video",
            description = "Get every Caption object in the database related to a Video",
            tags = {"captions", "get", "video"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Caption[].class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "API could not find data for that id, check format of id"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/videos/{id}/captions")
    public List<Caption> findCaptionsOfVideo(@Parameter(required = true, description = "Id of the video to search for captions") @PathVariable String id) throws Exception {
        try {
            Optional<Video> video = videoRepository.findById(id);
            if (!video.isPresent()) {
                throw new VideoNotFoundException(videoError);
            }
            return video.get().getCaptions();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

}
