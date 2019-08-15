package myblog.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("사용자 회원가입/조회/수정/삭제")
    void crud() throws Exception {
        UserCreatedDto expected = new UserCreatedDto("javajigi", "javajigi@nextstep.camp", "password");
        MvcResult createRequest = requestPost("/users", expected)
                .andExpect(status().isCreated())
                .andReturn();

        String location = createRequest.getResponse().getHeader("Location");
        logger.debug("location : {}", location);

        requestGet(location)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(expected.getUserId())))
                .andExpect(jsonPath("$.email", is(expected.getEmail())));

        UserUpdatedDto updatedDto = new UserUpdatedDto("ssosso", "ssossohow@gmail.com", "ssosso_password");

        requestPut(location, updatedDto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(updatedDto.getUserId())))
                .andExpect(jsonPath("$.email", is(updatedDto.getEmail())));


    }

    public ResultActions requestPost(String uri, Object jsonContent) throws Exception{
        return request(post(uri).content(asJsonString(jsonContent)));
    }

    public ResultActions requestGet(String uri) throws Exception{
        return request(get(uri));
    }

    public ResultActions requestPut(String uri, Object jsonContent) throws Exception {
        return request(put(uri).content(asJsonString(jsonContent)));
    }

    public ResultActions request(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(
                builder.contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
