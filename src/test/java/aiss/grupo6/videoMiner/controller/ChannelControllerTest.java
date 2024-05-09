package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.model.Channel;
import aiss.grupo6.videoMiner.model.Video;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChannelControllerTest {
    ChannelController channel;
    @Test
    public void findAll() throws Exception {
        List<Channel> channels = channel.findAll();
        assertNotNull(channels, "Channels list must not be null");
        if(!channels.isEmpty()) {
            for (Channel c : channels) {
                assertInstanceOf(String.class, c.getId(), "Channel id must be a string");
                assertInstanceOf(String.class, c.getName(), "Channel name must be a string");
                assertInstanceOf(String.class, c.getDescription(), "Channel description must be a string");
                assertInstanceOf(String.class, c.getCreatedTime(), "Channel creation time must be a string");
            }
        }
    }
    @Test
    public void findOne() throws Exception {
        try {
            Channel c = channel.findOne("2");
        } catch(HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void create() throws Exception{
        try {
            Channel c = channel.create(new Channel("893", "prueba de creacion",
                    "no creo que funcione",
                    "fecha de creacion: " + LocalDateTime.now().toString()));
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}