package picpay.desafio.picpay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import picpay.desafio.picpay.domain.user.User;
import picpay.desafio.picpay.dtos.NotificationDTO;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();

        NotificationDTO notification = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notification, String.class);
    
        if (notificationResponse.getStatusCode().isError()) {
            System.out.println("Error sending notification");
            throw new Exception("Error sending notification");
        }
    }
}
