package aiss.videominer.controller;

import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Video;
import aiss.videominer.repository.CaptionRepository;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public CaptionController(CaptionRepository repository) {
        this.repository=repository;
    }

    @GetMapping("/captions")
    public List<Caption> findAll() {
        return repository.findAll();
    }

    @GetMapping("/captions/{id}")
    public Caption findOne(@PathVariable String id) throws CaptionNotFoundException {
        Optional<Caption> caption=repository.findById(id);
        if (!caption.isPresent()) {
            throw new CaptionNotFoundException();
        }
        return caption.get();
    }

    @GetMapping("/videos/{id}/captions")
    public List<Caption> findCaptionsOfVideo(@PathVariable String id) throws VideoNotFoundException {
        Optional<Video> video = videoRepository.findById(id);
        if (!video.isPresent()) {
            throw new VideoNotFoundException();
        }
        return video.get().getCaptions();
    }

}
