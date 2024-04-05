package TasteMates.DiverseDish.comment;


import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("recipe/{recipeId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     */
    @PostMapping
    public String createComment(
            @PathVariable("recipeId")
            Long recipeId,
            @RequestParam("content")
            @Validated
            String content,
            BindingResult result,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model
    ) {

        /**
         * content가 null인 경우 Error 발생
         */
        if (result.hasErrors()) {
            return "redirect:/recipe/%d".formatted(recipeId);
        }

        if (customUserDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String username = customUserDetails.getUsername();
        ResponseCommentDto comment = commentService.createComment(recipeId, username, content);

        model.addAttribute("comment", comment);
        return "redirect:/recipe/%d".formatted(recipeId);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("{commentId}")
    public String deleteComment(
            @PathVariable("recipeId")
            Long recipeId,
            @PathVariable("commentId")
            Long commentId,
            Authentication authentication
    ) {
        String username = authentication.getName();

        commentService.deleteComment(recipeId, commentId, username);

        return "redirect:/recipe/%d".formatted(recipeId);
    }

}
