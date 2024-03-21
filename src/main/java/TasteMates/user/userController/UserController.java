package TasteMates.user.userController;

import TasteMates.user.dto.CreateUserDto;
import TasteMates.user.dto.UpdateUserDto;
import TasteMates.user.dto.UserDto;
import TasteMates.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("signup")
    public UserDto signUp(
            @RequestBody
            CreateUserDto dto
    ) {
        return userService.createUser(dto);
    }

    //회원정보 추가 후 ACTIVE유저로 전환
    @PutMapping("info")
    public UserDto signUpFinal(
        @RequestBody
        UpdateUserDto dto,
        String username
    ) {
        return userService.signUpFinal(dto, username);
    }

    //로그인
    @GetMapping("login")
    public String login(){
        return "login-form";
    }

    //회원정보 수정
    @PutMapping("update")
    public UserDto update(
            @RequestBody
            UserDto dto,
            String username
    ) {
        return userService.updateUser(dto, username);
    }
}
