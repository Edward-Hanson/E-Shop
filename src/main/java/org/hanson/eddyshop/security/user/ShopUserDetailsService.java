package org.hanson.eddyshop.security.user;

import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.exception.customizedExceptions.UserRelatedException;
import org.hanson.eddyshop.model.User;
import org.hanson.eddyshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(
                ()-> new UserRelatedException(ErrorConstant.USER_NOT_FOUND)
        );
        return ShopUserDetails.buildUserDetails(user);
    }
}
