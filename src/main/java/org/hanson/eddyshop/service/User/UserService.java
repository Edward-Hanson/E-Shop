package org.hanson.eddyshop.service.User;

import org.hanson.eddyshop.dto.request.user.CreateUserRequest;
import org.hanson.eddyshop.dto.request.user.UpdateUserRequest;
import org.hanson.eddyshop.dto.response.user.UserDto;
import org.hanson.eddyshop.model.User;

public interface UserService {
    UserDto getUserById(Long userId);
    UserDto createUser(CreateUserRequest request);
    String updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    User getAuthenticatedUser();
}
