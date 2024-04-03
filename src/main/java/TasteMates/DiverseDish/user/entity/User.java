package TasteMates.DiverseDish.user.entity;

import TasteMates.DiverseDish.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 처음 회원가입 할 때 필수 입력 사항
    @Column(nullable = false, unique = true)
    @Setter
    private String username;
    @Column(nullable = false)
    @Setter
    private String password;
    @Setter
    private String email;
    @Setter
    private String nickname;
    @Setter
    private String profileImage;
    @Setter
    private String role; //INACTIVE, ACTIVE, ADMIN (권한은 ,로 구분지어 저장)

    // 로그인 후 추가 입력 사항 (추가사항을 입력 해야 추천 레시피 등을 메인페이지에서 볼 수 있다.)
    @Setter
    private String gender; //male, female
    @Setter
    private String birth; //TODO 입력 받는 형식 어떻게 할지?
    @Setter
    private String interest; ////관심사-음식 타입

//    public static User toUserEntity(UserDto userDto) {
//        User userEntity = new User();
//        userEntity.setUsername(userDto.getUsername());
//        userEntity.setPassword(userDto.getPassword());
//        userEntity.setEmail(userDto.getEmail());
//        userEntity.setNickname(userDto.getNickname());
//        userEntity.setGender(userDto.getGender());
//        userEntity.setBirth(userDto.getBirth());
//        userEntity.setInterest(userDto.getInterest());
//
//        return userEntity;
//    }
}

