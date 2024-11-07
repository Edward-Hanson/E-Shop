package org.hanson.eddyshop.service.auth;

import org.hanson.eddyshop.dto.request.auth.LoginRequest;
import org.hanson.eddyshop.dto.response.auth.JwtResponse;

public interface AuthService {
    JwtResponse authenticate(LoginRequest request);
}
