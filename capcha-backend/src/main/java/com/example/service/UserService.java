package com.example.service;

import com.example.dto.RecaptchaResponse;
import com.example.dto.UserRequestDto;
import com.example.dto.UserResponse;
import com.example.exceptions.InvalidRequestException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CaptchaService captchaService;


    public UserResponse signup (UserRequestDto request) throws InvalidRequestException, ResourceNotFoundException {
        log.info("processing request to signup");
        User user = mapToEntity(request);
        RecaptchaResponse recaptchaResponse = captchaService.verify(request.token());

        if(!recaptchaResponse.isSuccess()){
            throw new InvalidRequestException("Captcha verification failed");
        }

        log.info("successfully verified captcha token");
        User savedUser = userRepository.save(user);


        return mapToResponse(savedUser.getEmail(),recaptchaResponse.getHostname());

    }

    private User mapToEntity(UserRequestDto request){
        User user =new User();
        if(request !=null){
            user.setEmail(request.email());
            user.setFirstName(request.firstName());
            user.setLastName(request.lastName());
            user.setPassword(request.password());

        }
        return user;
    }

    private UserResponse mapToResponse(String email,String hostname){
          return new UserResponse(email,hostname);
    }
}
