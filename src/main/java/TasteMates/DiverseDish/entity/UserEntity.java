package TasteMates.DiverseDish.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(unique = true)
    private String username;
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
    private String role; //INACTIVE, ACTIVE, ADMIN
    @Setter
    private String interest;
    @Setter
    private String status;

}
