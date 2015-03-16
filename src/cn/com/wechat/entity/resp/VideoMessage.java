package cn.com.wechat.entity.resp;

/**
 * 回复视频消息
 * @author z003bc3j
 *
 */
public class VideoMessage extends BaseMessage {

    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
    
}
