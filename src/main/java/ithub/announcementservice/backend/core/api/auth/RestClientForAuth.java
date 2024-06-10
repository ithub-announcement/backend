package ithub.announcementservice.backend.core.api.auth;

import ithub.announcementservice.backend.core.api.auth.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@PropertySource("classpath:application.properties")
public class RestClientForAuth {
  @Value("${urlServerAuth}")
  private String url;

  private String request(String token,String param){
    try {
      RestTemplate restTemplate = new RestTemplate();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<String> request = new HttpEntity<>(token, headers);
      String response = restTemplate.postForObject(url + param, request, String.class)
        .replaceAll("\"", "")
        .replaceAll("}", "");

      if (validateToken(response)) {
        return response;
      } else {
        throw new RuntimeException("Invalid token");
      }
    }catch (Exception err){
      throw new RuntimeException(err);
    }
  }

  private boolean validateToken(String response){
    if (response.split("status:")[1].split(",")[0].equals("OK")){
      return true;
    }
    return false;
  }

  public String getUserByToken(String token){
    return request(token, "/user/token").split("data:")[1];
  }

  public User getRoleAndUserByToken(String token){
    String current = request(token,"/user/token/role");
    User user = new User(current.split("uid:")[1].split(",")[0]
      ,current.split("role:")[1]);

    return user;
  }
}
