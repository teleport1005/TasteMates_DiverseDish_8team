package TasteMates.DiverseDish.user.userController;


import TasteMates.DiverseDish.auth.AuthenticationFacade;
import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import TasteMates.DiverseDish.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade authFacade;

    //회원가입 화면
    @GetMapping("/signup")
    public String signUpForm(){
        return "/user/signup-form";

    }
    //회원가입
    @PostMapping("/signup")
    public String signup(
            @ModelAttribute
            UserDto dto
    ) {
        log.info(dto.getEmail());
        log.info(dto.getPassword());
        log.info(dto.getUsername());
        userService.createUser(dto);
        return "redirect:/users/login";
    }

    //로그인
    @GetMapping("/login")
    public String login(UserDto dto){
        return "/user/login-form";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/users/login";
    }

    //회원 프로필 조회
    @GetMapping("/profiles")
    public String myProfile(
            Principal p,
            Model model
    ) {
        UserDto userDto = userService.myProfile();
        model.addAttribute("userInfo", userDto);
        return "user/my-profile";
    }

    //회원정보 수정 화면
    @GetMapping("/update")
    public String updateForm(Model model){
        UserDto userDto = userService.myProfile();
        model.addAttribute("userInfo", userDto);
        return "/user/update-form";
    }

    //회원정보 수정
    @PostMapping("/update")
    public String update(
            @ModelAttribute
            UserDto dto
    ) {
        log.info(dto.getUsername());
        log.info(dto.getEmail());
        log.info(dto.getNickname());
       userService.updateUser(dto);
       return "/user/login-form";
    }

    // 회원 프로필 사진 업로드
    @PutMapping("/{userId}/updateImg")
    public void updateImg (
            @PathVariable("userId")
            Long userId,
            @RequestParam("image")
            MultipartFile img
    ) {
        userService.updateProfileImage(userId, img);
    }


    //회원 탈퇴
    @DeleteMapping("{userId}")
    public void deleteUser(
            @PathVariable("userId")
            Long userId,
            Authentication authentication
    ) {
        userService.deleteUser(userId);
    }
}
