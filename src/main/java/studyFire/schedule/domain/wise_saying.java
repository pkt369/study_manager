package studyFire.schedule.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class wise_saying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wise_saying_id")
    private Long id;

    private String talker;
    private String content;


}
