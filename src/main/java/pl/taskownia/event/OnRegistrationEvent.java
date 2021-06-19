package pl.taskownia.event;

import org.springframework.context.ApplicationEvent;
import pl.taskownia.model.User;

public class OnRegistrationEvent extends ApplicationEvent {
    private User user;

    public OnRegistrationEvent(User user) {
        super(user);

        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
