package TasteMates.DiverseDish.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseReviewDto {

    private Long id;
    private String profileImage;
    private String username;
    private String content;

    public static ResponseReviewDto fromEntity(Review entity) {
        ResponseReviewDto dto = new ResponseReviewDto();
        dto.setId(entity.getId());
        dto.setProfileImage(entity.getImage());
        dto.setUsername(entity.getUser().getUsername());
        dto.setContent(entity.getContent());
        return dto;
    }
}
