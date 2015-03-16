package cn.com.wechat.entity.resp;

/**
 * 图片消息
 * @author zhqing
 *
 */
public class ImageMessage extends BaseMessage {

    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    
}
