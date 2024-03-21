package TasteMates.DiverseDish.comment_review.dto;

import TasteMates.DiverseDish.comment_review.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private String username;

    public static CommentDto fromEntity(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUser().getUsername());
        dto.setContent(entity.getContent());
        return dto;
    }
}
