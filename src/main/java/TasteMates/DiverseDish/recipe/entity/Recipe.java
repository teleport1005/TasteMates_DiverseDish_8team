package TasteMates.DiverseDish.recipe.entity;

<<<<<<< HEAD

import TasteMates.DiverseDish.user.entity.User;
=======
import TasteMates.DiverseDish.entity.UserEntity;
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
<<<<<<< HEAD
    private User user;
=======
    private UserEntity user;
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
    @Setter
    private String main_image;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private String video_link;
    @Setter
    private int view;

    @Setter
    @Enumerated(EnumType.STRING)
    private Level level;
    @Setter
    @Enumerated(EnumType.STRING)
    private Category category;

    @Setter
    private String  ingredient; // 추후 split
    @Setter
    @ColumnDefault("0")
    private int approval; // 0이면 비공개, 1이면 공개


    public enum Level {
        LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5
    }

    public enum Category {
<<<<<<< HEAD
        KOREAN, CHINESE, AMERICAN, JAPANESE, INDIAN
    }
=======
        KOREAN, CHINESE, AMERICAN, JAPANESE, SNACK
    }



>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
}
