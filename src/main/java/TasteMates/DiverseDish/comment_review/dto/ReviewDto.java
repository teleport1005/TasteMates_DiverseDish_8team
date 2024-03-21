package TasteMates.DiverseDish.comment_review.dto;

import TasteMates.DiverseDish.comment_review.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Long id;
    private String profileImage;
    private String username;
    private String content;

    public static ReviewDto fromEntity(Review entity) {
        ReviewDto dto = new ReviewDto();
        dto.setId(entity.getId());
        dto.setProfileImage(entity.getImage());
        dto.setUsername(entity.getUser().getUsername());
        dto.setContent(entity.getContent());
        return dto;
    }
}
