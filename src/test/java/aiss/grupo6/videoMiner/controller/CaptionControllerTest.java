package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.model.Caption;
import aiss.grupo6.videoMiner.model.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaptionControllerTest {
    CaptionController caption;
    @Test
    public void findAll() throws Exception {
        List<Caption> captions = caption.findAll();
        assertNotNull(captions, "No captions");
        if(!captions.isEmpty()) {
            for (Caption c : captions) {
                assertInstanceOf(String.class, c.getId(), "caption id must be a string");
                assertInstanceOf(String.class, c.getName(), "caption name must be a string");
                assertInstanceOf(String.class, c.getLanguage(), "caption language must be a string");
            }
        }
    }

    @Test
    void findOne() throws Exception {
        try {
            Caption c = caption.findOne("2");
        } catch(HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void findCaptionsOfVideo() throws Exception{
        try {
            List<Caption> captions = caption.findCaptionsOfVideo("1");
            for (Caption c : captions) {
                assertInstanceOf(String.class, c.getId(), "caption id must be a string");
                assertInstanceOf(String.class, c.getName(), "caption name must be a string");
                assertInstanceOf(String.class, c.getLanguage(), "caption language must be a string");
            }
        } catch(HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }

    }
}