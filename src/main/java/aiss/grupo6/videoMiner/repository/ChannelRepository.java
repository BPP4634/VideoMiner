package aiss.grupo6.videoMiner.repository;

import aiss.grupo6.videoMiner.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel,String> {
}
