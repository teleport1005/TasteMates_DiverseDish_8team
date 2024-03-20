package TasteMates.DiverseDish.recipe.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    @Setter
    private int step;
    @Setter
    private String cooking_tip;
    @Setter
    private String image;
}
