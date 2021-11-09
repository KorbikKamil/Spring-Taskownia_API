package pl.taskownia.data;

import lombok.Data;
import pl.taskownia.model.User;

@Data
public class UserDataUpdate {
    private String email;
    private User.MakerStatus makerStatus;
    private String firstName;
    private String lastName;
    private String phone;
    private String birthDate;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
