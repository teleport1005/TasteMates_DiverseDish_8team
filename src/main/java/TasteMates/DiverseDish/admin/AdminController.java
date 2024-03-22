package TasteMates.DiverseDish.admin;

import TasteMates.DiverseDish.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    //유저 조회
    @GetMapping("/users")
    public Page<UserDto> readAllUsers(
            Pageable pageable
    ) {
        return adminService.readUsers(pageable);
    }

    // 레시피 승인/거절

    // 엠디's pick
}
