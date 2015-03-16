package cn.com.wechat.entity.resp;

/**
 * 回复音乐消息
 * 
 * @author zhqing
 *
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private Music Music;

    public Music getMusic() {
	return Music;
    }

    public void setMusic(Music music) {
	Music = music;
    }
}
