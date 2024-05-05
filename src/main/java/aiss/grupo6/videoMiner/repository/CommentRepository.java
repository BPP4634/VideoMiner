package aiss.grupo6.videoMiner.repository;

import aiss.grupo6.videoMiner.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment,String>  {

}

//    List<Comment> comments = new ArrayList<>();
//
//    public CommentaryRepository() {
//        comments.add(new Comment("Caption1",
//                "Spanish",null));
//        comments.add(new Comment("Caption2",
//                "English",null));
//    }
//
//    public List<Comment> findAll() {
//        return comments;
//    }
//
//    public Comment findOne(String id) {
//        return comments.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
//    }
//
//}