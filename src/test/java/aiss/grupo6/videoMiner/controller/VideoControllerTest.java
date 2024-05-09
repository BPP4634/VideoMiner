package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.model.Caption;
import aiss.grupo6.videoMiner.model.Channel;
import aiss.grupo6.videoMiner.model.Video;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoControllerTest {

    VideoController video;
    @Test
    void findAll() throws Exception {
        List<Video> videos = video.findAll();
        assertNotNull(videos, "No videos");
        if(!videos.isEmpty()) {
            for (Video v : videos) {
                assertInstanceOf(String.class, v.getId(), "Video id must be a string");
                assertInstanceOf(String.class, v.getName(), "Video name must be a string");
                assertInstanceOf(String.class, v.getDescription(), "Video description must be a string");
                assertInstanceOf(String.class, v.getReleaseTime(), "Video release time must be a string");
                //alguna idea de como verificar que los caption y los comment son todos captions y comments? ya me entendeis
            }
        }
    }

    @Test
    void findOne() throws Exception{
        try {
            Video c = video.findOne("2");
        } catch(HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void findVideosOfChannel() throws Exception{
        try{
            List<Video> videosDeCanal = video.findVideosOfChannel("1");
        }catch(HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}