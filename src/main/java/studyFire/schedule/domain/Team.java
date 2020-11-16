package studyFire.schedule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    private String isAchieve;
    private int team_point;
    private int achieve_rate;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private void setChat(Chat chat) {
        this.chat = chat;
    }

    private void setIsAchieve(String isAchieve) {
        this.isAchieve = isAchieve;
    }

    private void setTeam_point(int team_point) {
        this.team_point = team_point;
    }

    private void setAchieve_rate(int achieve_rate) {
        this.achieve_rate = achieve_rate;
    }

    //== 생성 메서드 ==//
    public static Team createTeam() {
        Team team = new Team();
        team.setIsAchieve("false");
        team.setTeam_point(0);
        team.setAchieve_rate(0);
        team.setChat(new Chat());

        return team;
    }

}
