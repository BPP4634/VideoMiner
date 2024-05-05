package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.InternalErrorException;
import aiss.grupo6.videoMiner.repository.CaptionRepository;
import aiss.grupo6.videoMiner.repository.VideoRepository;
import aiss.grupo6.videoMiner.exception.CaptionNotFoundException;
import aiss.grupo6.videoMiner.exception.VideoNotFoundException;
import aiss.grupo6.videoMiner.model.Caption;
import aiss.grupo6.videoMiner.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/captions")
    public List<Caption> findAll() throws Exception{
        try{
            return repository.findAll();
        } catch(RuntimeException e) {
            throw new InternalErrorException(internalError);
        }

    }

    @GetMapping("/captions/{id}")
    public Caption findOne(@PathVariable String id) throws Exception {
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

    @GetMapping("/videos/{id}/captions")
    public List<Caption> findCaptionsOfVideo(@PathVariable String id) throws Exception {
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
