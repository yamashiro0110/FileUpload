import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.domain.User;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static java.lang.System.*;

public class RequestTest {

    public static void main(String[] args) throws JsonProcessingException {
        RequestTest test = new RequestTest();
        test.postUser();
        test.postUserFile();
        test.postUserFileJsonString();
        test.postUserFileJsonType();
    }

    private final User user = new User("hoge", "fuga", 20);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileSystemResource resource = new FileSystemResource("hogehoge.jpg");

    public RequestTest() {
    }

    private void postUser() {
        final String url = "http://localhost:8080/user/";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(user, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        dump(responseEntity);
    }

    private void postUserFile() {
        final String url = "http://localhost:8080/user/file";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("user", user);
        map.add("file", resource);
        HttpEntity<?> httpEntity = new HttpEntity<>(map, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        dump(responseEntity);
    }

    private void postUserFileJsonString() throws JsonProcessingException {
        final String url = "http://localhost:8080/user/file/json/str";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("user", objectMapper.writeValueAsString(user));
        map.add("file", resource);
        HttpEntity<?> httpEntity = new HttpEntity<>(map, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        dump(responseEntity);
    }

    private void postUserFileJsonType() {
        final String url = "http://localhost:8080/user/file/json/type";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("user", user);
        map.add("file", resource);
        HttpEntity<?> httpEntity = new HttpEntity<>(map, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        dump(responseEntity);
    }

    private void dump(ResponseEntity<?> responseEntity) {
        out.println("*****");
        out.println("status code: " + responseEntity.getStatusCode());
        out.println("response body: " + responseEntity.getBody());
        out.println("*****");
    }

}
