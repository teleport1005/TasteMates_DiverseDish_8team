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
    private String role; //ACTIVE, ADMIN (권한은 ,로 구분지어 저장)
    @Setter
    private String birth;
    @Setter
    private String interest; ////관심사-음식 타입(한식,중식,일식,양식,간식)
}

