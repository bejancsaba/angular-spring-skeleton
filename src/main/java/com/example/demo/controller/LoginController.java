package com.example.demo.controller;

import com.example.demo.domain.LoginRequest;
import com.example.demo.security.JwtBuilderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.example.demo.security.JwtBuilderService.INVALID_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final JwtBuilderService jwtBuilderService;

    @ApiOperation(
            value = "Performs user authentication",
            notes = "Adds JWT token as Authorization response header when authentication succeeds."
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 401, message = "Unauthorized"),
                    @ApiResponse(code = 200, message = "Authentication succeeded")
            }
    )
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public void attemptLogin(@ApiParam(value = "The credentials of the user.", required = true)
                                 @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Login attempt for: " + loginRequest.getUsername());
        String jwt = jwtBuilderService.createToken(loginRequest.getUsername(), loginRequest.getPassword());
        log.info("JWT created: " + jwt);
        if (INVALID_TOKEN.equals(jwt)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        else {
            response.addHeader(AUTHORIZATION,"Bearer " + jwt);
        }
    }
}
