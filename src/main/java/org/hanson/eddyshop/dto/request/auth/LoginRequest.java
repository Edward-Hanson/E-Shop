package org.hanson.eddyshop.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(@Email String email,@NotNull String password) {
}
