package aiss.grupo6.videoMiner.repository;

import aiss.grupo6.videoMiner.model.Caption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptionRepository extends JpaRepository<Caption,String>  {


//    List<Caption> captions = new ArrayList<>();
//
////    public CaptionRepository() {
////        captions.add(new Caption(UUID.randomUUID().toString(),"Caption1",
////                "Spanish"));
////        captions.add(new Caption(UUID.randomUUID().toString()."Caption2",
////                "English"));
////    }
//
//    public List<Caption> findAll() {
//        return captions;
//    }
//
//    public Caption findOne(String id) {
//        return captions.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
//    }

}