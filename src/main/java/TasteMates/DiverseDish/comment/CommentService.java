package TasteMates.DiverseDish.comment;

import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.recipe.repo.RecipeRepository;
import TasteMates.DiverseDish.user.entity.User;
import TasteMates.DiverseDish.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 생성
    public ResponseCommentDto createComment(Long recipeId, String username, String content) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        Comment comment = Comment.createComment(recipe, user, content);

        return ResponseCommentDto.fromEntity(commentRepository.save(comment));
    }

    // 조회
    public List<ResponseCommentDto> readAllComments(Long recipeId) {
        List<ResponseCommentDto> list = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByRecipe_idOrderByIdAsc(recipeId);
        for (int i = 0; i < commentList.size(); i++) {
            list.add(ResponseCommentDto.fromEntity(commentList.get(i)));
        }
        return list;
    }

    // 삭제
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
