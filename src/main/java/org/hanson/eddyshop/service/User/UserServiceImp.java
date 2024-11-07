package org.hanson.eddyshop.service.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.constants.SuccessConstant;
import org.hanson.eddyshop.dto.request.user.CreateUserRequest;
import org.hanson.eddyshop.dto.request.user.UpdateUserRequest;
import org.hanson.eddyshop.dto.response.user.UserDto;
import org.hanson.eddyshop.exception.customizedExceptions.UserRelatedException;
import org.hanson.eddyshop.model.User;
import org.hanson.eddyshop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::mapToUserDto)
                .orElseThrow(()-> new UserRelatedException(ErrorConstant.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user->!userRepository.existsByEmail(request.email()))
                .map(req->{
                    User user = new User();
                    user.setEmail(request.email());
                    user.setPassword(request.password());
                    user.setFirstName(request.firstName());
                    user.setLastName(request.lastName());
                    userRepository.save(user);
                    return mapToUserDto(user);
                }).orElseThrow(()->new UserRelatedException("Oops "+ request.email()+" "+ ErrorConstant.ALREADY_EXISTS));
    }

    @Override
    @Transactional
    public String updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser->{
            existingUser.setFirstName(request.firstName());
            existingUser.setLastName(request.lastName());
            userRepository.save(existingUser);
            return SuccessConstant.UPDATED;
        }).orElseThrow(()->new UserRelatedException(ErrorConstant.USER_NOT_FOUND));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new UserRelatedException(ErrorConstant.USER_NOT_FOUND);
        });
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new UserRelatedException(ErrorConstant.USER_NOT_FOUND);
        }
        String email =  auth.getName();
        return userRepository.findByEmail(email);
    }

    private UserDto mapToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
