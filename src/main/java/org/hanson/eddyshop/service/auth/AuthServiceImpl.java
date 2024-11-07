package org.hanson.eddyshop.service.auth;


import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.dto.request.auth.LoginRequest;
import org.hanson.eddyshop.dto.response.auth.JwtResponse;
import org.hanson.eddyshop.model.User;
import org.hanson.eddyshop.repository.UserRepository;
import org.hanson.eddyshop.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse authenticate(LoginRequest request) {
        User user = Optional.ofNullable(userRepository.findByEmail(request.email()))
                .orElseThrow(() -> new UsernameNotFoundException(ErrorConstant.USER_NOT_FOUND));
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        String jwt = jwtUtils.generateTokenForUser(auth);
        return new JwtResponse(user.getId(), jwt);

    }
}
