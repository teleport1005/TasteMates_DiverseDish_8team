package TasteMates.DiverseDish.user.userController;


import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import TasteMates.DiverseDish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public UserDto signUp(
            @RequestBody
            UserDto dto
    ) {
        return userService.createUser(dto);
    }

    //회원정보 추가 후 ACTIVE유저로 전환
    @PutMapping("/info")
    public UserDto signUpFinal(
        @RequestBody
        UserDto dto,
        String username
    ) {
        return userService.additionalInfo(dto, username);
    }

    //로그인
    @GetMapping("/login")
    public String login(){
        return "login-form";
    }

    //회원 프로필 조회
    @GetMapping("/myProfile")
    public UserDto myProfile()
    {
        return userService.myProfile();
    }

    //회원기 정보 수정
    @PutMapping("/update")
    public UserDto update(
            @RequestBody
            UserDto dto,
            String username
    ) {
        return userService.updateUser(dto, username);
    }

    // 회원 프로필 사진 업데이트
    @PutMapping("/{userId}/updateImg")
    public void updateImg (
            @PathVariable("userId")
            Long userId,
            @RequestParam("image")
            MultipartFile img
    ) {
        userService.updateProfileImage(userId, img);

    }
}
