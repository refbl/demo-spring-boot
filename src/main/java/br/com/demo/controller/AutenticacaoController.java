package br.com.demo.controller;

import br.com.demo.config.security.TokenService;
import br.com.demo.controller.dto.TokenDto;
import br.com.demo.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm loginForm){
        System.out.println("Dados Autenticacao :" + loginForm.getEmail() + " ::: " + loginForm.getSenha());

        UsernamePasswordAuthenticationToken dadosLogin = loginForm.converter();

        try{
            var authentication = this.authManager.authenticate(dadosLogin);

            // Geracao do Token
            String token = tokenService.gerarToken(authentication);

            System.out.println("Token : " + token);

            return ResponseEntity.ok(new TokenDto(token, "Bearer"));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();

        }


    }
}
