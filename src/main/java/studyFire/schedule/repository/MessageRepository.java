package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.Chat;
import studyFire.schedule.domain.ChatMessage;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    private final EntityManager em;

    public void save(ChatMessage message) {
        em.persist(message);
    }

    //Chat의 정보를 가져와서 해결.
    public List<ChatMessage> findByChat(Chat chat) {
        Long chatId = em.find(Chat.class, chat.getId()).getId();

        return em.createQuery("select m from ChatMessage m where m.chat = :chatId", ChatMessage.class)
                .setParameter("chatId", chatId)
                .getResultList();
    }
}
