package org.hanson.eddyshop.dto.request.user;

public record CreateUserRequest(String firstName,String lastName,
                                String email,String password) {
}
