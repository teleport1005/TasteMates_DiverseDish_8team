package TasteMates.DiverseDish.admin;

import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    //회원 조회
    public Page<UserDto> readUsers (Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDto::fromEntity);

    }

    // 레시피 승인/거절

    // 엠디's pick
}
