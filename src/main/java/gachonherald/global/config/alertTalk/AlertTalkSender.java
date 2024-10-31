package gachonherald.global.config.alertTalk;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AlertTalkSender {

    @Value("${solapi.apikey}")
    private String SOLAPI_API_KEY;

    @Value("${solapi.secretkey}")
    private String SOLAPI_SECRET_API_KEY;

    @Value("${solapi.pf-id}")
    private String SOLAPI_PF_ID;

    @Value("${solapi.template-id}")
    private String SOLAPI_START_CHAT_TEMPLATE_ID;
    private String SOLAPI_WELCOME_TEMPLATE_ID;

    @Value("${solapi.from-number}")
    private String SOLAPI_FROM_NUMBER;

    public void sendKakaoAlertTalk(String nickname, String title, String number, TemplateType templateType) {
        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(SOLAPI_API_KEY, SOLAPI_SECRET_API_KEY, "https://api.solapi.com");

        KakaoOption kakaoOption = new KakaoOption();
        kakaoOption.setPfId(SOLAPI_PF_ID);

        kakaoOption.setDisableSms(true);

        if (templateType == TemplateType.START_CHAT) {
            kakaoOption.setTemplateId(SOLAPI_START_CHAT_TEMPLATE_ID);
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{nickname}", nickname);
            variables.put("#{title}", title);
            kakaoOption.setVariables(variables);
        } else if (templateType == TemplateType.WELCOME) {
            kakaoOption.setTemplateId(SOLAPI_START_CHAT_TEMPLATE_ID);
        }

        net.nurigo.sdk.message.model.Message message = new net.nurigo.sdk.message.model.Message();
        message.setFrom(SOLAPI_FROM_NUMBER);
        message.setTo(number);
        message.setKakaoOptions(kakaoOption);

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

}
