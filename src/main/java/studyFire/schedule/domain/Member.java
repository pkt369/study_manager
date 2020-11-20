package studyFire.schedule.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChatMessage> messages = new ArrayList<>();

    @Column(unique = true)
    private String email;

    private String password;
    private String name;
    private int age;
    private int member_point;

    private void setName(String name) {
        this.name = name;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setAge(int age) {
        this.age = age;
    }

    protected void setMember_point(int member_point) {
        this.member_point = member_point;
    }


    //== 생성 메서드 ==//
    public static Member createMember(String email, String password, String name, int age) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setAge(age);
        member.setMember_point(0);

        return member;
    }



}
