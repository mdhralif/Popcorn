package com.kaankaplan.userService.business.concretes;

import com.kaankaplan.userService.business.abstracts.UserService;
import com.kaankaplan.userService.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.getUserByEmail(email);

            if(user == null) {
                throw new UsernameNotFoundException("User not found: " + email);
            }

            if(user.getClaim() == null) {
                throw new UsernameNotFoundException("User claim is null for: " + email);
            }

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getClaim().getClaimName()));

            System.out.println("✅ User loaded: " + email + " with role: ROLE_" + user.getClaim().getClaimName());

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), authorities);
        } catch (Exception e) {
            System.err.println("❌ Error loading user " + email + ": " + e.getMessage());
            throw new UsernameNotFoundException("Authentication failed for: " + email);
        }
    }

}
