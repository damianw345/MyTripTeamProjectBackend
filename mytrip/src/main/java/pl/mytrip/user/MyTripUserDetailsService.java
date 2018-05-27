package pl.mytrip.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MyTripUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            User user = userRepository.findOne(username);
            if (user == null) {
                return null;
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), Collections.emptySet());
        }
        catch (Exception ex){
            throw new UsernameNotFoundException("User not found", ex);
        }
    }

}