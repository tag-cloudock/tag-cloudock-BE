package pagether.domain.voice.application;

import pagether.domain.count.application.CountService;
import pagether.domain.voice.domain.Voice;
import pagether.domain.voice.dto.req.VoiceRequest;
import pagether.domain.voice.dto.res.VoicesResponse;
import pagether.domain.voice.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VoiceService {
    private final VoiceRepository voiceRepository;
    private final CountService countService;

    public void save(VoiceRequest request) throws Exception {

        Voice voice = Voice.builder()
                .phoneNumber(request.getPhoneNumber())
                .opinion(request.getOpinion())
                .build();

        voiceRepository.save(voice);
    }

    public List<VoicesResponse> get() {
        List<VoicesResponse> voicesDTO = new ArrayList<>();
        List<Voice> voices = voiceRepository.findAllByOrderByVoiceIdDesc();
        for (Voice voice : voices) {
            VoicesResponse dto = new VoicesResponse();
            dto.setVoiceId(voice.getVoiceId());
            dto.setOpinion(voice.getOpinion());
            voicesDTO.add(dto);
        }
        countService.count("home");
        return voicesDTO;
    }
}