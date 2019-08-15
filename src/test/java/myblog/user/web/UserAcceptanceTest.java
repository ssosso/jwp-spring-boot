package myblog.user.web;

import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserDto;
import myblog.user.dto.UserUpdatedDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.*;
import reactor.core.publisher.Mono;
import support.test.AcceptanceTest;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Autowired
    private WebTestClient client;

    @Test
    @DisplayName("사용자 회원가입/조회/수정/삭제")
    void crud() {
        UserCreatedDto expectedCreateUser = new UserCreatedDto("javajigi", "javajigi@nextstep.camp", "password");
        EntityExchangeResult<byte[]> response = requestWithBody(client.post(), "/users", expectedCreateUser, UserCreatedDto.class)
                .expectStatus().isCreated()
                .expectBody()
                .returnResult();

        URI location = response.getResponseHeaders().getLocation();
        logger.debug("location : {}", location);
        UserDto actualCreateUser = request(client.get(), location)
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();

        assertThat(actualCreateUser.getUserId()).isEqualTo(expectedCreateUser.getUserId());
        assertThat(actualCreateUser.getEmail()).isEqualTo(expectedCreateUser.getEmail());

        UserUpdatedDto expectedUpdateUser = new UserUpdatedDto("ssosso", "ssossohow@gmail.com", "ssosso_password");

        UserDto actualUpdateUser = requestWithBody(client.put(), location, expectedUpdateUser, UserUpdatedDto.class)
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();

        assertThat(actualUpdateUser.getUserId()).isEqualTo(expectedUpdateUser.getUserId());
        assertThat(actualUpdateUser.getEmail()).isEqualTo(expectedUpdateUser.getEmail());

        request(client.delete(), location)
                .expectStatus().isNoContent()
                .expectBody()
                .returnResult();
    }

    public <T> ResponseSpec requestWithBody(RequestBodyUriSpec requestBodyUriSpec,
                                            String uri,
                                            T bodyContent,
                                            Class<T> bodyContentClass) {
        return requestBodyUriSpec
                .uri(uri)
                .body(Mono.just(bodyContent), bodyContentClass)
                .exchange();
    }

    public <T> ResponseSpec requestWithBody(RequestBodyUriSpec requestBodyUriSpec,
                                            URI uri,
                                            T bodyContent,
                                            Class<T> bodyContentClass) {
        return requestBodyUriSpec
                .uri(uri.toString())
                .body(Mono.just(bodyContent), bodyContentClass)
                .exchange();
    }

    public ResponseSpec request(RequestHeadersUriSpec requestHeadersUriSpec,
                                URI uri) {
        return requestHeadersUriSpec
                .uri(uri.toString())
                .exchange();
    }
}
