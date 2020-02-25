package br.com.demo.config.security;

import br.com.demo.model.Usuario;
import br.com.demo.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;


    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // Validar o Token e Autenticar o Usuario
        String token = recuperarToken(httpServletRequest);

        boolean valido = tokenService.isTokenValido(token);

        System.out.println(valido);

        if (valido){
            autenticarCliente(token);
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void autenticarCliente(String token) {

        Long idUsuario = tokenService.getIdUsuario(token);

        Optional<Usuario> optionalUsuario = this.usuarioRepository.findById(idUsuario);


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(optionalUsuario.get(), null, optionalUsuario.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest httpServletRequest) {
        System.out.println("Entrou no recuperarToken");
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer")){
            return null;
        }

        return token.substring(7, token.length());

    }
}
