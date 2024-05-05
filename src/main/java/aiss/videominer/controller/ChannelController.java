package aiss.videominer.controller;

import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.ChannelRepository;
import aiss.videominer.repository.CommentRepository;
import aiss.videominer.repository.VideoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    public ChannelController(ChannelRepository repository) {
        this.repository=repository;
    }

    @GetMapping
    public List<Channel> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Channel findOne(@PathVariable String id) throws VideoNotFoundException {
        Optional<Channel> channel = repository.findById(id);
        if (!channel.isPresent()) {
            throw new VideoNotFoundException();
        }
        return channel.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel) {
        Channel newChannel = repository.save(new Channel(channel.getId(),channel.getName(),
                channel.getDescription(),channel.getCreatedTime(),channel.getVideos()));
        return newChannel;
    }

}
