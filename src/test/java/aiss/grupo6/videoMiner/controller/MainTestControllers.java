package aiss.grupo6.videoMiner.controller;

import aiss.grupo6.videoMiner.exception.*;
import aiss.grupo6.videoMiner.model.*;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MainTestControllers {

    @Autowired
    ChannelController controllerCha;

    @Autowired
    VideoController controllerVid;

    @Autowired
    CommentController controllerCom;

    @Autowired
    CaptionController controllerCap;

    private Channel channel;
    private Video video;
    private List<Video> listaVideos;
    private Caption caption;
    private List<Caption> listaCaptions;
    private Comment comment;
    private List<Comment> listaComentarios;
    private User user;

    @Before
    public void create() throws Exception {
        user = new User("Si", "soyUnLinkValido","noSeQuePone");
        comment = new Comment("33","Madrid campeon", "1 de junio",user);
        listaComentarios = new ArrayList<>();
        listaComentarios.add(comment);

        caption = new Caption("53498","Patata","Espanol de Espana");
        listaCaptions = new ArrayList<>();
        listaCaptions.add(caption);

        video = new Video("hola", "asdf", "asdf", "asdf",listaComentarios,listaCaptions);
        listaVideos = new ArrayList<>();
        listaVideos.add(video);

        channel = new Channel("893", "prueba de creacion",
                "no creo que funcione", "fecha de creacion: Antequera", listaVideos);
        channel = controllerCha.create(channel);
    }
//------------------------------------------------------------------------------------//
//------------------------------ChannelControllerTest---------------------------------//
//------------------------------------------------------------------------------------//

    @Test
    public void findAllChannels() throws Exception {
        List<Channel> channels = controllerCha.findAll();
        System.out.println(channels);
        assertNotNull(channels, "Channels list must not be null");
        assertFalse(channels.isEmpty(), "Channels must not be empty after creation");
        boolean encontrado = false;
        for (Channel c : channels) {
            if(c.equals(channel)) {
                encontrado = true;
                break;
            }
        }
        assertTrue(encontrado, "Channel uploaded must be found");

    }
    @Test
    public void findOneChannel() throws Exception {
        Channel c = controllerCha.findOne(channel.getId());
        assertInstanceOf(String.class, c.getId(), "Channel id must be a string");
        assertInstanceOf(String.class, c.getName(), "Channel name must be a string");
        assertInstanceOf(String.class, c.getDescription(), "Channel description must be a string");
        assertInstanceOf(String.class, c.getCreatedTime(), "Channel creation time must be a string");
        assertEquals(channel, c, "Channel must be the one created before");
    }

    @Test
    public void findOneChannelError() throws Exception {
        try {
            Channel c = controllerCha.findOne("sijaja");
        } catch (ChannelNotFoundException e) {
            assertInstanceOf(ChannelNotFoundException.class, e);
        }
    }

    @Test
    public void createChannelError() throws Exception{
        try {
            Channel c = controllerCha.create(null);
        } catch (InternalErrorException e) {
            assertInstanceOf(InternalErrorException.class, e);
        }
    }
//------------------------------------------------------------------------------------//
//------------------------------VideoControllerTest-----------------------------------//
//------------------------------------------------------------------------------------//

    @Test
    public void findAllVideos() throws Exception {
        List<Video> videos = controllerVid.findAll();
        System.out.println(videos);
        assertNotNull(videos, "Videos list must not be null");
        assertFalse(videos.isEmpty(), "Videos list must not be empty after creation");
        boolean encontrado = false;
        for (Video v: videos) {
            if (v.getId().equals(video.getId())) {
                encontrado = true;
                break;
            }
        }
        assertTrue(encontrado, "Video created before must be on database");
    }

    @Test
    public void findOneVideo() throws Exception {
        Video v = controllerVid.findOne(video.getId());
        assertInstanceOf(String.class, v.getId(), "Video id must be a string");
        assertInstanceOf(String.class, v.getName(), "Video name must be a string");
        assertInstanceOf(String.class, v.getDescription(), "Video description must be a string");
        assertInstanceOf(String.class, v.getReleaseTime(), "Video release time must be a string");
        assertEquals(video, v, "Video must be the one created before");
    }

    @Test
    public void findOneVideoError() throws Exception {
        try {
            Video v = controllerVid.findOne("yekee");
        } catch (VideoNotFoundException e) {
            assertInstanceOf(VideoNotFoundException.class, e);
        }
    }

    @Test
    public void findVideosOfChannel() throws Exception {
        List<Video> channelVideos = controllerVid.findVideosOfChannel(channel.getId());
        assertNotNull(channelVideos, "Video list from the channel must not be null");
        assertFalse(channelVideos.isEmpty(), "Video list from the channel must not be empty");
        assertEquals(listaVideos, new ArrayList<>(channelVideos), "The video uploaded must be found");
    }

    @Test
    public void notFoundVideosOfChannel() throws Exception {
        List<Video> channelVideos = controllerVid.findVideosOfChannel(channel.getId());
        assertNotNull(channelVideos, "Video list from the channel must not be null");
        assertFalse(channelVideos.isEmpty(), "Video list from the channel must not be empty");
        boolean encontrado = false;
        for(Video v: channelVideos) {
            if(v.getId().equals("EsteVideoEsDeMurcia")) {
                encontrado = true;
                break;
            }
        }
        assertFalse(encontrado, "The video we are looking for, doesn't exist");
    }

    @Test
    public void findVideosOfChannelError() throws Exception {
        try {
            List<Video> channelVideos = controllerVid.findVideosOfChannel("oaishfd");
        } catch (ChannelNotFoundException e) {
            assertInstanceOf(ChannelNotFoundException.class, e);
        }
    }

//------------------------------------------------------------------------------------//
//------------------------------CommentControllerTest---------------------------------//
//------------------------------------------------------------------------------------//

    @Test
    public void findAllComments() throws Exception {
        List<Comment> comments = controllerCom.findAll();
        assertNotNull(comments, "Comments list must not be null");
        assertFalse(comments.isEmpty(), "Comments list must not be empty after creation");
        boolean encontrado = false;
        for (Comment c: comments) {
            if (c.getId().equals(comment.getId())){
                encontrado = true;
                break;
            }
        }
        assertTrue(encontrado, "Comment uploaded must be found");
    }

    @Test
    public void findOneComent() throws Exception {
        Comment c = controllerCom.findOne(comment.getId());
        assertInstanceOf(String.class, c.getId(), "Comment id must be a string");
        assertInstanceOf(String.class, c.getText(), "Comment text must be a string");
        assertInstanceOf(String.class, c.getCreatedOn(), "Comment creation time must be a string");
        assertInstanceOf(Long.class, c.getAuthor().getId(), "Comment's author must be a long");
        assertInstanceOf(String.class, c.getAuthor().getName(), "Comment's author's name must be a string");
        assertInstanceOf(String.class, c.getAuthor().getUser_link(), "Comment's author's user link must be a string");
        assertInstanceOf(String.class, c.getAuthor().getPicture_link(), "Comment's author's picture link must be a string");
        assertEquals(comment, c, "Comment must be the one created before");
        assertEquals(user, c.getAuthor(), "The author of the comment must be the one created before");
    }

    @Test
    public void findOneCommentError() throws Exception {
        try {
            Comment c = controllerCom.findOne("meQuedoCiego");
        } catch (CommentNotFoundException e) {
            assertInstanceOf(CommentNotFoundException.class, e);
        }
    }

    @Test
    public void findCommentsOfVideo() throws Exception{
        List<Comment> videoComments = controllerCom.findCommentsOfVideo(video.getId());
        assertNotNull(videoComments, "Comments list from the video must not be null");
        assertFalse(videoComments.isEmpty(), "Comments list from the video must not be empty");
        assertEquals(listaComentarios, videoComments, "The comment uploaded must be found");
    }

    @Test
    public void notFoundCommentsOfVideo() throws Exception {
        List<Comment> videoComments = controllerCom.findCommentsOfVideo(video.getId());
        assertNotNull(videoComments, "Comments list from the video must not be null");
        assertFalse(videoComments.isEmpty(), "Comments list from the video must not be empty");
        boolean encontrado = false;
        for(Comment c: videoComments) {
            if(c.getId().equals("numeroDos")) {
                encontrado = true;
                break;
            }
        }
        assertFalse(encontrado, "The comment we are looking for, doesn't exist");
    }

    @Test
    public void findCommentsOfVideoError() throws Exception{
        try {
            List<Comment> videoComments = controllerCom.findCommentsOfVideo("oaishfd");
        } catch (VideoNotFoundException e) {
            assertInstanceOf(VideoNotFoundException.class, e);
        }
    }

//------------------------------------------------------------------------------------//
//------------------------------CaptionControllerTest---------------------------------//
//------------------------------------------------------------------------------------//

    @Test
    public void findAllCaptions() throws Exception {
        List<Caption> captions = controllerCap.findAll();
        assertNotNull(captions, "Captions list must not be null");
        assertFalse(captions.isEmpty(), "Captions list must not be empty after creation");
        boolean encontrado = false;
        for (Caption c: captions) {
            if (c.getId().equals(caption.getId())){
                encontrado = true;
                break;
            }
        }
        assertTrue(encontrado, "Caption uploaded must be found");
    }

    @Test
    public void findOneCaption() throws Exception {
        Caption c = controllerCap.findOne(caption.getId());
        assertInstanceOf(String.class, c.getId(), "Caption id must be a string");
        assertInstanceOf(String.class, c.getName(), "Caption name must be a string");
        assertInstanceOf(String.class, c.getLanguage(), "Caption language must be a string");
        assertEquals(caption, c, "Caption must be the one created before");
    }

    @Test
    public void findOneCaptionError() throws Exception {
        try {
            Caption c = controllerCap.findOne("meLoInventoYa");
        } catch (CaptionNotFoundException e) {
            assertInstanceOf(CaptionNotFoundException.class, e);
        }
    }

    @Test
    public void findCaptionsOfVideo() throws Exception {
        List<Caption> videoCaptions = controllerCap.findCaptionsOfVideo(video.getId());
        assertNotNull(videoCaptions, "Captions list from the video must not be null");
        assertFalse(videoCaptions.isEmpty(), "Captions list from the video must not be empty");
        assertEquals(listaCaptions, videoCaptions, "The caption uploaded to the video must be found");
    }

    @Test
    public void notFoundCaptionsOfVideo() throws Exception {
        List<Caption> videoCaptions = controllerCap.findCaptionsOfVideo(video.getId());
        assertNotNull(videoCaptions, "Captions list from the video must not be null");
        assertFalse(videoCaptions.isEmpty(), "Captions list from the video must not be empty");
        boolean encontrado = false;
        for(Caption c: videoCaptions) {
            if(c.getId().equals("yiijaaa")) {
                encontrado = true;
                break;
            }
        }
        assertFalse(encontrado, "The caption we are looking for, doesn't exist");
    }

    @Test
    public void findCaptionsOfVideoError() throws Exception {
        try {
            List<Caption> videoCaptions = controllerCap.findCaptionsOfVideo("oaishfd");
        } catch (VideoNotFoundException e) {
            assertInstanceOf(VideoNotFoundException.class, e);
        }
    }
}