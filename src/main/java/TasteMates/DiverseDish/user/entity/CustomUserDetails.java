package TasteMates.DiverseDish.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String birth;
    private String interest;
    private String authorities;
    private User entity;


    public String getRawAuthorities() {
        return this.authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            if (this.authorities != null) {
//                String[] rawAuthorities = authorities.split(",");
//                for (String rawAuthority: rawAuthorities) {
//                    grantedAuthorities.add(new SimpleGrantedAuthority(rawAuthority));
//                }
//            }
            grantedAuthorities.add(new SimpleGrantedAuthority(this.authorities));

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            System.out.println("grantedAuthority.toString() = " + grantedAuthority.toString());
        }

            return grantedAuthorities;
        }

    public static CustomUserDetails fromEntity(User entity){
        return CustomUserDetails.builder()
                .entity(entity)
                .build();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


