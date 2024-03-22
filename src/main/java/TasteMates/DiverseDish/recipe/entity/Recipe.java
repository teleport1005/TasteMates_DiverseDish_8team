package TasteMates.DiverseDish.recipe.entity;

import TasteMates.DiverseDish.entity.UserEntity;
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
    private UserEntity user;
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
        KOREAN, CHINESE, AMERICAN, JAPANESE, INDIAN
    }



}
