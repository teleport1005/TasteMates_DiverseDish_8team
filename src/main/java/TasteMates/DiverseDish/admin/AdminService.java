package TasteMates.DiverseDish.admin;

import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.entity.User;
import TasteMates.DiverseDish.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    //회원 전체 조회
    public List<UserDto> readUsers(){
        List<UserDto> userList = new ArrayList<>();
        List<User> users =userRepository.findAll();
        for (User user: users) {
            userList.add(UserDto.fromEntity(user));
        }
        return userList;
    }

    //회원 단일 조회
    public UserDto readOneUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserDto.fromEntity(user);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    //회원 삭제
    public void delete(Long id) {
        //관리자인지 확인 -시큐리티까지 합치고 완성하기

        if (userRepository.existsById(id))
            userRepository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
    //TODO

    // 레시피 승인/거절

    // 엠디's pick

