package pagether.domain.voice.repository;

import pagether.domain.voice.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VoiceRepository extends JpaRepository<Voice, Long> {
    List<Voice> findAllByOrderByVoiceIdDesc();

}