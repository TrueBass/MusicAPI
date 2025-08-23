package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.user_dtos.*;
import com.example.musicapi.dtos.refresh_token_dtos.ResponseTokenDto;
import com.example.musicapi.entities.User;

public interface IUserService {
    ResponseTokenDto registerUser(UserAuthDto userDto);

    ResponseTokenDto loginUser(UserLoginDto userDto);

    ResponseTokenDto refreshUser(String refreshToken);

    void logoutUser(String refreshToken);

    UserDto getUserByUsername(String username);

    void updatePassword(UpdatePasswordDto updatePasswordDto);

    void updateEmail(UpdateEmailDto updateEmailDto);

    String updateUsername(UpdateUsernameDto updateUsernameDto);

    User getCurrentUser();

    void deleteUser(Long userId);

    int updateSocialCredit(Long userId, int newCredit);
}
