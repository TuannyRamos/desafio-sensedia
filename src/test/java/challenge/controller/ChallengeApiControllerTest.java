package challenge.controller;

import challenge.dto.CredentialDTO;
import challenge.dto.SecretKeyDTO;
import challenge.dto.StatusReponseDTO;
import challenge.service.AuthenticatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(value = ChallengeApiController.class)
@RunWith(SpringRunner.class)
public class ChallengeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticatorService authenticatorService;

    @Test
    public void retrieveGenerationKey() throws Exception {

        Mockito.when(authenticatorService.generateKey()).thenReturn(new SecretKeyDTO("abc123"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/tokens");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String expected = "{\"secretKey\": \"abc123\"}";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void retrieveValidatedCode() throws Exception {

        Mockito.when(authenticatorService.validateCode(new CredentialDTO("abc123", Long.valueOf(123456))))
               .thenReturn(new StatusReponseDTO(true));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/tokens")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"generatedKey\": \"abc123\", \"authCode\":123456}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String expected = "{\"status\": true}";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
