package studyFire.schedule.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    private String email;

    private String password;
    private String name;
    private int age;
}
