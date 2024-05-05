package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.repository.ChannelRepository;
import aiss.grupo6.videoMiner.exception.ChannelNotFoundException;
import aiss.grupo6.videoMiner.model.Channel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    @Value( "${message.channelNotFound}" )
    private String channelError;

    @Value( "${message.internalError}" )
    private String internalError;

    @GetMapping("/channels")
    public List<Channel> findAll() throws Exception{
        try{
            return repository.findAll();
        } catch(RuntimeException e) {
            throw new InternalErrorException(internalError);
        }
    }

    @GetMapping("/channels/{id}")
    public Channel findOne(@PathVariable String id) throws Exception {
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/channels")
    public Channel create(@Valid @RequestBody Channel channel) throws Exception {
        Channel newChannel = repository.save(new Channel(channel.getId(),channel.getName(),
                channel.getDescription(),channel.getCreatedTime(),channel.getVideos()));
        return newChannel;
    }

}
