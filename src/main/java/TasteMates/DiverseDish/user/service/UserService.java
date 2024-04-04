package TasteMates.DiverseDish.user.service;

import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.repo.UserRepository;
import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import TasteMates.DiverseDish.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //회원가입
    public void createUser(UserDto dto) {
       //  유저가 이미 존재할 경우 오류
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        // 비밀번호 입력 안 할 경우 오류
        if (dto.getPassword()==null) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        UserDto.fromEntity(userRepository.save(User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .birth(dto.getBirth())
                .gender(dto.getGender())
                //.profileImage(dto.getProfileImage())
                .role("ROLE_INACTIVE")
                .build()));
    }

    //회원정보 추가 후 ACTIVE 유저로 전환
    public UserDto additionalInfo(UserDto dto, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        User user = optionalUser.get();
         user.setGender(dto.getGender());
         user.setBirth(dto.getBirth());
         user.setInterest(dto.getInterest());
         user.setRole("ROLE_ACTIVE");
        return UserDto.fromEntity(userRepository.save(user));
    }


    // 회원 프로필 조회
    public UserDto myProfile(){
        //인증 이용하여 회원 이름 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("myProfile() = {}", authentication.toString());
        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserDto.fromEntity(user);
//            log.info("user = {}", user.toString());
        }
        log.info("We could not find your account.");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    //로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> optionalUser = userRepository.findByUsername(username);
       if (optionalUser.isEmpty())
           throw new UsernameNotFoundException(username);
       User user = optionalUser.get();

       log.info("Authority of found user = {}", user.getRole());

       return CustomUserDetails.builder()
               .username(user.getUsername())
               .password(user.getPassword())
               .authorities(user.getRole())
               .build();
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    //회원 정보 수정
    public UserDto updateUser(UserDto dto, String username) {
        //TODO
        // 보안 관련 정보가져오기
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        User existingUser = optionalUser.get();
            existingUser.setEmail(dto.getEmail());
            existingUser.setNickname(dto.getNickname());
            existingUser.setGender(dto.getGender());
            existingUser.setBirth(dto.getBirth());
            existingUser.setInterest(dto.getInterest());
        return UserDto.fromEntity(userRepository.save(existingUser));
        }


    // 회원 프로필 사진 업데이트
    public void updateProfileImage(Long id, MultipartFile profileImage) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        String profileDir = String.format("media/%d/", id);
        try {
            Files.createDirectories(Paths.get(profileDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String originFilename = profileImage.getOriginalFilename();
        String[] fileNameSplit = originFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length-1];
        String profileFileName = "profile." + extension;
        log.info(profileFileName);

        String profilePath = profileDir + profileFileName;
        log.info(profilePath);
        try{
            profileImage.transferTo(Path.of(profilePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String requestPath = String.format("/static/%d/%s", id, profileFileName);
        log.info(requestPath);
        User target = optionalUser.get();
        target.setProfileImage(requestPath);
        userRepository.save(target);
    }

    // 회원 탈퇴
    public void deleteUser(Long userId) {

    }
}

