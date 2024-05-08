package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.repository.ChannelRepository;
import aiss.grupo6.videoMiner.exception.ChannelNotFoundException;
import aiss.grupo6.videoMiner.model.Channel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Channel",
        description = "Integration for an standardized model for channels in local H2 database"
)
@RestController
@RequestMapping("/videominer")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    @Value( "${message.channelNotFound}" )
    private String channelError;

    @Value( "${message.internalError}" )
    private String internalError;

    @Operation(
            summary = "Retrieve all Channels",
            description = "Get every Channel object in the database",
            tags = {"channels", "get", "all"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Channel[].class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/channels")
    public List<Channel> findAll() throws Exception{
        try{
            return repository.findAll();
        } catch(RuntimeException e) {
            throw new InternalErrorException(internalError);
        }
    }

    @Operation(
            summary = "Retrieve a Channel by Id",
            description = "Get a Channel object by its id",
            tags = {"channels", "get"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "API could not find data for that id, check format of id"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @GetMapping("/channels/{id}")
    public Channel findOne(@Parameter(required = true, description = "Id of the channel to search") @PathVariable String id) throws Exception {
        try {
            Optional<Channel> channel = repository.findById(id);
            if (!channel.isPresent()) {
                throw new ChannelNotFoundException(channelError);
            }
            return channel.get();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }
    }

    @Operation(
            summary = "Upload a Channel",
            description = "Upload a Channel object to the database, defined inside the body request",
            tags = {"channels", "post"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json") }, description = "Everything was fine"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema()), description = "Format of channel is not correct, please check all necessary fields are filled"),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema()), description = "Database may not be accessible at the moment, please try again later or check connections")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/channels")
    public Channel create(@Parameter(required = true, description = "Channel object to be uploaded to the database") @RequestBody @Valid Channel channel) throws Exception {
        try{
            Channel newChannel = repository.save(new Channel(channel.getId(),channel.getName(),
                    channel.getDescription(),channel.getCreatedTime(),channel.getVideos()));
            return newChannel;
        } catch(RuntimeException e) {
            throw new InternalErrorException(internalError);
        }

    }

}
