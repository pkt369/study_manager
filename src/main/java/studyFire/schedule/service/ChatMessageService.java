package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.Chat;
import studyFire.schedule.domain.ChatMessage;
import studyFire.schedule.repository.MemberRepository;
import studyFire.schedule.repository.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    //메세지 보내기
    public Long sendMessage(ChatMessage chatMessage) {
        messageRepository.save(chatMessage);
        return chatMessage.getId();
    }

    //채팅에 들어가있는 메세지 찾기
    public List<ChatMessage> findMessageInChat(Chat chat) {
        return messageRepository.findByChat(chat);
    }
}
