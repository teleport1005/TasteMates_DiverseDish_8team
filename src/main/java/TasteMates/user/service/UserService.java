package TasteMates.user.service;

import TasteMates.user.dto.CreateUserDto;
import TasteMates.user.dto.UpdateUserDto;
import TasteMates.user.dto.UserDto;
import TasteMates.user.entity.RecipeUserDetails;
import TasteMates.user.entity.UserEntity;
import TasteMates.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    //회원가입
    public UserDto createUser(CreateUserDto dto) {
        // 비밀번호 체크 jwt 생성 후 만들기
//        if (!dto.getPassword().equals(dto.getPasswordCheck()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return UserDto.fromEntity(userRepository.save(UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build()));
    }

    //회원정보 추가 후 INACTIVE 유저로 전환
    public UserDto signUpFinal(UpdateUserDto dto, String username) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        UserEntity userEntity = optionalUser.get();
        userEntity.setGender(dto.getGender());
        userEntity.setBirth(dto.getBirth());
        userEntity.setInterest(dto.getInterest());
        return UserDto.fromEntity(userRepository.save(userEntity));
    }


    //회원 조회
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
       if (optionalUser.isEmpty())
           throw new UsernameNotFoundException(username);
       UserEntity userEntity = optionalUser.get();
       return RecipeUserDetails.builder()
               .username(userEntity.getUsername())
               .password(userEntity.getPassword())
               .email(userEntity.getEmail())
               .nickname(userEntity.getNickname())
               .profileImage(userEntity.getProfileImage())
               .gender(userEntity.getGender())
               .birth(userEntity.getBirth())
               .interest(userEntity.getInterest())
               .status(userEntity.getStatus())
               .roles(userEntity.getRole())
               .build();
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    //회원 정보 업데이트
    public UserDto updateUser(UserDto dto, String username) {
        //TODO
        // 보안 관련 정보가져오기
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        UserEntity existingUser = optionalUser.get();
        existingUser.setEmail(dto.getEmail());
        existingUser.setNickname(dto.getNickname());
        existingUser.setGender(dto.getGender());
        existingUser.setBirth(dto.getBirth());
        existingUser.setInterest(dto.getInterest());
        return UserDto.fromEntity(userRepository.save(existingUser));
        }


    // 회원 프로필 사진 업데이트




    //회원 탈퇴 신청
    public void requestDelete(String username) {
        Optional<UserEntity> newUser = userRepository.findByUsername(username);
        if (newUser.isPresent()) {
            UserEntity targetUser = newUser.get();
            targetUser.setStatus("탈퇴 대기 중"); //사용자를 탈퇴 대기 중으로 변경
            userRepository.save(targetUser);
        }
    }


    //회원 탈퇴 신청 수락



}

