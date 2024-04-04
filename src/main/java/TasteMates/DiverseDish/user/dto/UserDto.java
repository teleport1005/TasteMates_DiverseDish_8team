package TasteMates.DiverseDish.user.dto;

import TasteMates.DiverseDish.user.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String profileImage;
    private String gender;
    private String birth;
    private String interest;

    public static UserDto fromEntity(User entity){
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .profileImage(entity.getProfileImage())
                .gender(entity.getGender())
                .birth(entity.getBirth())
                .interest(entity.getInterest())
                .build();
    }
}
