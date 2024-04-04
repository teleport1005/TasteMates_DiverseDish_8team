package TasteMates.DiverseDish.admin;

import TasteMates.DiverseDish.user.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    //회원 전체 조회
    @GetMapping("/users")
    public List<UserDto> readAllUsers() {
        return adminService.readUsers();
    }

    //회원 단일 조회
    @GetMapping("/{userId}")
    public UserDto readOneUser(
        @PathVariable("userId")
        Long id
    ){
        return adminService.readOneUser(id);
    }

    //회원 삭제
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id")
            Long id
    ) {
        adminService.delete(id);

    }
    // 레시피 승인/거절

    // 엠디's pick
}
