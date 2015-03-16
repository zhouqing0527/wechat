package cn.com.wechat.entity.resp;

/**
 * 回复语音消息
 * @author zhqing
 *
 */
public class VoiceMessage extends BaseMessage {

    private Voice voice;

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
    
    
}
