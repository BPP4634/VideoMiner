package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.model.Channel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChannelControllerTest {
    ChannelController channel;
    @Test
    public void findAll() throws Exception{
        assertNotNull(channel, "Channels must not be null");
        try {
            List<Channel> res = channel.findAll();
        } catch (HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
    @Test
    public void findOne() {
    }

    @Test
    public void create() {
    }
}