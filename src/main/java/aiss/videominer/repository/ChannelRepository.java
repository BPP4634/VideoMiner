package aiss.videominer.repository;

import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel,String> {
}
