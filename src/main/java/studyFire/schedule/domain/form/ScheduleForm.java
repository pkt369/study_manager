package studyFire.schedule.domain.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ScheduleForm {

    private LocalDate date;
    private String content_header;
    private String content_body;
}
