package TasteMates.DiverseDish.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCommentDto {

    private Long id;
    private String profileImage;
    private String content;
    private String username;

    public static ResponseCommentDto fromEntity(Comment entity) {
        ResponseCommentDto dto = new ResponseCommentDto();
        dto.setId(entity.getId());
        dto.setProfileImage(entity.getUser().getProfileImage());
        dto.setUsername(entity.getUser().getUsername());
        dto.setContent(entity.getContent());
        return dto;
    }
}
