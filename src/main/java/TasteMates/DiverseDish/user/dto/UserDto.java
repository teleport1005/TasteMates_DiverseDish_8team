package TasteMates.DiverseDish.user.dto;

import TasteMates.DiverseDish.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long user_id;
    private String username;
    @Setter
    private String password;
    @Setter
    private String email;
    @Setter
    private String nickname;
    @Setter
    private String profileImage;
    @Setter
    private String gender;
    @Setter
    private String birth;
    @Setter
    private String interest;
//    @Setter
//    private String status; //탈퇴 대기 중/ 탈퇴 완료

    public static UserDto fromEntity(User entity){
        return UserDto.builder()
                .user_id(entity.getUser_id())
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
