package studyFire.schedule.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinTeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team; //고아객체 이용해보기


    //== 생성메서드 ==//
    public static JoinTeamMember createJoinTeamMember(Member member, Team team) {
        JoinTeamMember joinTeamMember = new JoinTeamMember();
        joinTeamMember.member = member;
        joinTeamMember.team = team;

        return joinTeamMember;
    }

    //== 편의 메서드 ==//
    public void setTeam(Team team) {
        this.team = team;
        team.getJoinTeamMembers().add(this);
    }

}
