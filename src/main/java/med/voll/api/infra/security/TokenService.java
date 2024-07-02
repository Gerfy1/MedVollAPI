package med.voll.api.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("{api.security.token.secret")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Api Voll.med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataexpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
           return JWT.require(algoritmo)
                    .withIssuer("Api Voll.med")
                    .build()
                   .verify(tokenJWT)
                   .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT invalido!" +tokenJWT);
        }
    }

    private Instant dataexpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
