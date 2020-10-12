package hr.tvz.java.web.susac.joke.controller;

import hr.tvz.java.web.susac.joke.dto.user.LoginDTO;
import hr.tvz.java.web.susac.joke.dto.user.RegisterDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;
import hr.tvz.java.web.susac.joke.security.jwt.JwtFilter;
import hr.tvz.java.web.susac.joke.security.jwt.TokenProvider;
import hr.tvz.java.web.susac.joke.service.UserService;
import hr.tvz.java.web.susac.joke.service.VerificationService;
import hr.tvz.java.web.susac.joke.util.validation.ValidationErrorPrinter;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final VerificationService verificationService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @GetMapping("/{username}")
    public ResponseEntity<?> getOneByUsername(@PathVariable("username") String username){
        UserDTO userDTO = userService.findOneByUsernameEnabled(username);

        if(Objects.isNull(userDTO))
            return new ResponseEntity<>("User does not exists!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, Errors errors){
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO, Errors errors){
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
