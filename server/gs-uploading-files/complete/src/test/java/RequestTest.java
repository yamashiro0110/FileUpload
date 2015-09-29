import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.domain.User;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.System.out;

public class RequestTest {

    public static void main(String[] args) throws IOException {
        RequestTest test = new RequestTest();
        test.postUser();
        test.postUserFile();
        test.postUserFileJsonString();
        test.postUserFileJsonType();
        test.getImage();
    }

    private static final String HOST = "http://localhost:8888";
    private final User user = new User("hoge", "fuga", 20);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileSystemResource resource = new FileSystemResource("hogehoge.jpg");

    public RequestTest() {
    }

    private void postUser() {
        final String url = HOST + "/user/";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(user, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        dump(responseEntity);
    }

    private void postUserFile() {
        final String url = HOST + "/user/file";
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
        final String url = HOST + "/user/file/json/str";
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
        final String url = HOST + "/user/file/json/type";
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

    private void getImage() throws IOException {
        final String url = HOST + "/download";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        ResponseEntity<byte[]> responseEntity = template.exchange(url, HttpMethod.GET, httpEntity, byte[].class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Files.write(Paths.get("download.jpg"), responseEntity.getBody());
        }
    }

    private void dump(ResponseEntity<?> responseEntity) {
        out.println("*****");
        out.println("status code: " + responseEntity.getStatusCode());
        out.println("response body: " + responseEntity.getBody());
        out.println("*****");
    }

}
