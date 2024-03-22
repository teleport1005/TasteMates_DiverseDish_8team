package TasteMates.DiverseDish.user.entity;

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

    // 로그인 후 추가 입력 사항 (입력해야 추천 레시피 등을 메인페이지에서 볼 수 있다.)
    @Setter
    private String gender; //male, female
    @Setter
    private String birth; //TODO 입력 받는 형식 어떻게 할지?
    @Setter
    private String interest; //

//    @Setter
//    private String status; //탈퇴 신청 상태 관련. 탈퇴 대기중, 탈퇴 완료
}
