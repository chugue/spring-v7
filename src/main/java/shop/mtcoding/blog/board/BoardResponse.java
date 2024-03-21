package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    @AllArgsConstructor
    @Data
    public static class CountDTO {
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        private Long replyCount;

    }

    @Data
    public static class DetailDTO {
        private int id;
        private String title;
        private String content;
        private int userId;
        private String username; // 게시글 작성자 이름
        private List<ReplyDTO> replies = new ArrayList<>();
        private boolean isOwner;

        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();  // join해서 들고 있다.
            this.isOwner = isOwner;
            if (sessionUser != null) {
                if (sessionUser.getId() == userId) {
                    isOwner = true;
                }
            }
            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();
        }

        // 여기서만 쓸거기 때문에 ReplyDTO는 static을 붙일 필요 없다.
        @Data
        public class ReplyDTO {
            private int id;
            private String comment;
            private int userId;  // 댓글작성자 아이디
            private String username; // 댓글작성자 이름
            private boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();  // 이때 레이지 로딩이 걸린다.
                this.comment = reply.getComment();
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();
                this.isOwner = false;
                if (sessionUser != null) {
                    if (sessionUser.getId() == userId) {
                        isOwner = true;
                    }
                }
            }
        }
    }

    @Data
    public static class MainDTO {
        private int id;
        private String title;

        public MainDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
        }
    }
}

