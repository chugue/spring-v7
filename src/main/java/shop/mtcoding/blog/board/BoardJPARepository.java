package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BoardJPARepository extends JpaRepository<Board, Integer> {

    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") int id);

    @Query("select b from Board b join fetch b.user left join fetch b.replies r join fetch r.user ru where b.id = :id")
    Board findDetail(@Param("id") int id);

    @Query("select new shop.mtcoding.blog.board.BoardResponse$CountDTO(b.id, b.title, b.content, b.user.id, (select count(r.id) from Reply r where r.board.id = b.id)) from Board b")
    List<BoardResponse.CountDTO> findAllWithReplyCount();
}
