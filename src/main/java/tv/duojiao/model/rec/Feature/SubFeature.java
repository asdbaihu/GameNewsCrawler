package tv.duojiao.model.rec.Feature;

import java.util.Arrays;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/26
 */
public class SubFeature {
    public String category;
    public NameEnity[] content;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public NameEnity[] getContent() {
        return content;
    }

    public void setContent(NameEnity[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SubFeature{" +
                "category='" + category + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}