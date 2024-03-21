package shop.mtcoding.blog.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.mtcoding.blog.board.Board;

import java.util.List;

public interface ReplyJPARepository extends JpaRepository<Reply, Integer> {



    @Query("select r from Reply r where r.board.id = :boardId")
    List<Reply> findByBoardId(@Param("boardId") int boardId);
}
