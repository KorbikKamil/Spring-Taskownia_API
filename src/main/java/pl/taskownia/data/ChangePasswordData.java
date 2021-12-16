package pl.taskownia.data;

import lombok.Data;

@Data
public class ChangePasswordData {
    String oldPassword;
    String newPassword;
}
