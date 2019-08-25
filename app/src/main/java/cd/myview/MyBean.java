package cd.myview;

/**
 * @Description: java类作用描述
 * @Author: 陈达
 * @CreateDate: 2019/8/25 13:32
 * @UpdateUser: 陈达
 * @UpdateDate: 2019/8/25 13:32
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MyBean {

    private String slideContent;

    public MyBean(String slideContent) {
        this.slideContent = slideContent;
    }

    public String getSlideContent() {
        return slideContent;
    }

    public void setSlideContent(String slideContent) {
        this.slideContent = slideContent;
    }
}
