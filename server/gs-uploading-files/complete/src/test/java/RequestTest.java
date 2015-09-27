import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.domain.User;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RequestTest {

    public static void main(String[] args) throws JsonProcessingException {
        RequestTest test = new RequestTest();
        test.postUser();
        test.postUserFile();
        test.postUserFileJson();
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
        System.out.println(String.format("StatusCode:%s, Body:%s", responseEntity.getStatusCode().name(), responseEntity.getBody()));
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
        System.out.println(String.format("StatusCode:%s, Body:%s", responseEntity.getStatusCode().name(), responseEntity.getBody()));
    }

    private void postUserFileJson() throws JsonProcessingException {
        final String url = "http://localhost:8080/user/file/json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("user", objectMapper.writeValueAsString(user));
        map.add("file", resource);
        HttpEntity<?> httpEntity = new HttpEntity<>(map, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(String.format("StatusCode:%s, Body:%s", responseEntity.getStatusCode().name(), responseEntity.getBody()));
    }

}
