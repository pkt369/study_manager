package studyFire.schedule.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WiseSaying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wise_saying_id")
    private Long id;

    private String talker;
    private String content;


    //== 생성 메서드 ==//
    public static WiseSaying createWiseSaying(String talker, String content) {
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.setTalker(talker);
        wiseSaying.setContent(content);

        return wiseSaying;
    }

}
