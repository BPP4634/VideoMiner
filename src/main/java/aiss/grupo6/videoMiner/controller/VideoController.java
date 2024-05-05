package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.repository.VideoRepository;
import aiss.grupo6.videoMiner.exception.VideoNotFoundException;
import aiss.grupo6.videoMiner.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer")
public class VideoController {


    @Autowired
    VideoRepository repository;

    @Value( "${message.videoNotFound}" )
    private String videoError;

    @Value( "${message.internalError}" )
    private String internalError;

    @GetMapping("/videos")
    public List<Video> findAll() throws Exception{
        try{
            return repository.findAll();
        } catch (RuntimeException e){
            throw new InternalErrorException(internalError);
        }

    }

    @GetMapping("/videos/{id}")
    public Video findOne(@PathVariable String id) throws Exception {
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

}
