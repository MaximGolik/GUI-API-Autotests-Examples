package ReqresTests.ApiTests.ReqresTestsPOJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserLoginAndRegistrationPOJO {
    private String email;
    private String password;

    public UserLoginAndRegistrationPOJO(String email) {
        this.email = email;
    }
}
