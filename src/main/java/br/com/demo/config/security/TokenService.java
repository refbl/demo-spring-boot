package br.com.demo.config.security;

import br.com.demo.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${demo.jwt.expiration}")
    private String expiration;

    @Value("${demo.jwt.secret}")
    private String secret;


    public String gerarToken(Authentication authentication) {

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        Date dataExpiracao = new Date(Calendar.getInstance().getTimeInMillis() + Long.parseLong(this.expiration));

        return Jwts.builder()
                .setIssuer("Api Teste")
                .setSubject(usuarioLogado.getId().toString())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean isTokenValido(String token) {
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Long getIdUsuario(String token) {

        var body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

        return Long.parseLong(body.getSubject());

    }
}
