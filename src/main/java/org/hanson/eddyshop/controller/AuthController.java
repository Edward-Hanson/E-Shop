package org.hanson.eddyshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.dto.request.auth.LoginRequest;
import org.hanson.eddyshop.dto.response.auth.JwtResponse;
import org.hanson.eddyshop.service.auth.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    @ResponseStatus(OK)
    public JwtResponse authenticate(@RequestBody @Valid LoginRequest request) {
        return authService.authenticate(request);
    }
}
