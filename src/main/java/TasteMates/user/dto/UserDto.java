package TasteMates.user.dto;

import TasteMates.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long user_id;
    private String username;
    private String password; //일단 넣어둠. 추후 삭제 여부 확인
    private String email;
    private String nickname;
    private String profileImage;
    private String gender;
    private String birth;
    private String interest;
    private String status; //탈퇴 대기 중/ 탈퇴 완료

    public static UserDto fromEntity(UserEntity entity){
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
