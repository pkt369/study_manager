package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.WiseSaying;
import studyFire.schedule.repository.WiseSayingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public Long save(WiseSaying wiseSaying) {
        wiseSayingRepository.save(wiseSaying);
        return wiseSaying.getId();
    }

    //한번에 3개가져오기(총 90개넣기)
    public List<WiseSaying> bringWiseSaying() {
        Random random = new Random();
        int ran1 = 0;
        int ran2 = 0;
        int ran3 = 0;
        do{
            ran1 = random.nextInt(90) + 1;
            ran2 = random.nextInt(90) + 1;
            ran3 = random.nextInt(90) + 1;
        }while(!((ran1 != ran2) && (ran1 != ran3) && (ran2 != ran3)));

        Long r1 = Integer.valueOf(ran1).longValue();
        Long r2 = Integer.valueOf(ran2).longValue();
        Long r3 = Integer.valueOf(ran3).longValue();

        WiseSaying saying1 = wiseSayingRepository.findOne(r1);
        WiseSaying saying2 = wiseSayingRepository.findOne(r2);
        WiseSaying saying3 = wiseSayingRepository.findOne(r3);

        List<WiseSaying> wiseSayingList = new ArrayList<>();
        wiseSayingList.add(saying1);
        wiseSayingList.add(saying2);
        wiseSayingList.add(saying3);

        return wiseSayingList;
    }

}
