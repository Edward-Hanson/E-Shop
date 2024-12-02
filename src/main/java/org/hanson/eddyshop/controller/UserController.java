package org.hanson.eddyshop.controller;


import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.dto.request.user.CreateUserRequest;
import org.hanson.eddyshop.dto.request.user.UpdateUserRequest;
import org.hanson.eddyshop.dto.response.user.UserDto;
import org.hanson.eddyshop.service.User.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @GetMapping("{userId}")
    @ResponseStatus(OK)
    public UserDto getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PostMapping("add")
    @ResponseStatus(CREATED)
    public UserDto  createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @PutMapping("update/{userId}")
    @ResponseStatus(OK)
    public String updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId){
        return userService.updateUser(request,userId);
    }

    @DeleteMapping("{userId}/delete")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long userId){
         userService.deleteUser(userId);
    }
}
