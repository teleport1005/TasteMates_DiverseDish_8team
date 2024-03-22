package TasteMates.DiverseDish.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public ResponseCommentDto createComment(Long recipeId, String username, String content) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        Comment comment = Comment.createComment(recipe, user, content);

        return ResponseCommentDto.fromEntity(commentRepository.save(comment));
    }

    public void deleteComment(Long recipeId, Long commentId, String username) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 댓글의 레시피가 맞는지
        if (!comment.getRecipe().equals(recipe)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 댓글을 작성한 유저가 맞는지
        if (!comment.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        commentRepository.deleteById(commentId);
    }
}
