package ru.tanaxxt.currencysystem.controllers;


//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/test/")
//@RequiredArgsConstructor
//@Tag(name = "Authentication")
public class TestController {

    @GetMapping("ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}

//    @PostMapping("ping")
//    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid @Parameter(description = "Formatted request with credentials") SignUpRequest request) {
//    }