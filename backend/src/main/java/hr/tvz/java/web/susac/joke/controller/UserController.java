package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.search.UserSearchDTO;
import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;
import hr.tvz.java.web.susac.joke.dto.user.RegisterDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.security.jwt.JwtFilter;
import hr.tvz.java.web.susac.joke.security.jwt.TokenProvider;
import hr.tvz.java.web.susac.joke.service.UserService;
import hr.tvz.java.web.susac.joke.service.VerificationService;
import hr.tvz.java.web.susac.joke.util.validation.ValidationErrorPrinter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/user")
@Api(description = "Contains API operations for User model.")
public class UserController {

    private final UserService userService;
    private final VerificationService verificationService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "Retrieves User by it's username.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays User]", response = UserDTO.class),
            @ApiResponse(code = 404, message = "User does not exists!", response = void.class)
    })
    @GetMapping("/{username}")
    public ResponseEntity<?> getOneByUsername(@PathVariable("username") String username){
        UserDTO userDTO = userService.findOneByUsernameEnabled(username);

        if(Objects.isNull(userDTO))
            return new ResponseEntity<>("User does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a User List by it's given params.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays a User List]", response = void.class),
            @ApiResponse(code = 204, message = "User list is empty!", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]", response = void.class)
    })
    @PostMapping("/by-search")
    public ResponseEntity<?> getAllBySearch(@Valid @RequestBody UserSearchDTO userSearchDTO,
                                            @ApiIgnore Errors errors){
        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        List<UserDTO> userDTOList = userService.findAllByParam(userSearchDTO);

        if(CollectionUtils.isEmpty(userDTOList))
            return new ResponseEntity<>("User list is empty!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Allows User authentication.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[Displays JWT token upon successful authorization]", response = JWTToken.class),
            @ApiResponse(code = 403, message = "[Invalid User credentials]\n" +
                    "[User's account is not activated]", response = void.class),
            @ApiResponse(code = 406, message = "[Failed validation]", response = void.class)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, @ApiIgnore Errors errors){
        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(!userService.isEnabled(loginDTO))
            return new ResponseEntity<>("Your account is not enabled!", HttpStatus.UNAUTHORIZED);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "Allows User registration.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Account successfully created! " +
                    "You will receive activation link on email address!", response = void.class),
            @ApiResponse(code = 403, message = "[Failed validation]\n" +
                    "[Username and/or E-mail address already taken]", response = void.class)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO,
                                      @ApiIgnore Errors errors){
        if(errors.hasErrors())
            return ValidationErrorPrinter.showValidationError(errors);

        if(!userService.isFreeUsername(registerDTO) || !userService.isFreeEmail(registerDTO)
            || !userService.doPasswordsMatch(registerDTO))
            return showOtherValidationError(registerDTO);

        try{
            userService.register(registerDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("Account successfully created!" + " " +
                "You will receive activation link on email address!", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Upon successful registration, a verification token is sent to User's " +
            "e-mail address to activate their account.",
            notes = "Account will be deleted if it is not activated within three days.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Account successfully created! " +
                    "You will receive activation link on email address", response = void.class),
            @ApiResponse(code = 403, message = "Your verification token is invalid or has expired!", response = void.class)
    })
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<?> accountVerification(@PathVariable("token") String token){

        if(!verificationService.isValid(token))
            return new ResponseEntity<>("Your verification token is invalid or has expired!", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Account is successfully activated!" + " " +
                "You can now log-in!", HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class JWTToken {
        private String token;
    }

    public ResponseEntity<?> showOtherValidationError(RegisterDTO registerDTO){
        String error = "";
        System.out.println(registerDTO.toString());

        if(!userService.isFreeUsername(registerDTO))
            error += "Username already taken!\n";

        if(!userService.isFreeEmail(registerDTO))
            error += "Email address already taken!\n";

        if(!userService.doPasswordsMatch(registerDTO))
            error += "Passwords must match each other!\n";


        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
