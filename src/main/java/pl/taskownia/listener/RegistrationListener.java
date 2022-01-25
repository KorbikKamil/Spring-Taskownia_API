package pl.taskownia.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.taskownia.event.OnRegistrationEvent;
import pl.taskownia.model.User;
import pl.taskownia.service.UserService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationEvent> {
    private static String httpType;
    private static String serverAddress;
    private static String emailAddress;

    @Value("${app.email-address}")
    public void setEmailAddress(String emailAddressTemp) {
        emailAddress = emailAddressTemp;
    }

    @Value("${app.http}")
    public void setHttpType(String httpTypeTemp) {
        httpType = httpTypeTemp;
    }

    @Value("${app.address}")
    public void setServerAddress(String serverAddressTemp) {
        serverAddress = serverAddressTemp;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(OnRegistrationEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createConfirmationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Potwierdzenie rejestracji";
        String confirmationUrl = "/user/registration-confirm?token=" + token;
        String message = "Pomyślnie zarejestrowano, aby potwierdzić rejestracje, kliknij w link poniżej:";

        SimpleMailMessage email = new SimpleMailMessage();
        System.out.println("3");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom(emailAddress);
        email.setText(message + "\r\n" + httpType + "://" + serverAddress + confirmationUrl);
        System.out.println("3.5");
        javaMailSender.send(email);
        System.out.println("4");
    }
}
