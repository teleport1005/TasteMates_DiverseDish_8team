package TasteMates.DiverseDish.recipe.entity;

import TasteMates.DiverseDish.entity.UserEntity;
import TasteMates.DiverseDish.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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
    private User user;
    @Setter
    private String main_image_url;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private String video_link;

    @Setter
    @Enumerated(EnumType.STRING)
    private Level level;

    @Setter
    @Enumerated(EnumType.STRING)
    private Category category;

    @Setter
    private String  ingredient; // 추후 split

    @Setter
    @OneToMany
    List<CookOrder> CookOrderList = new ArrayList<>();

    public enum Level {
        LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5
    }

    public enum Category {
        KOREAN, CHINESE, AMERICAN, JAPANESE, SNACK
    }



}