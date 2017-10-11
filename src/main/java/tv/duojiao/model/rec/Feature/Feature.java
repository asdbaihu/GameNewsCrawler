package tv.duojiao.model.rec.Feature;

import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/25
 */
public class Feature {
    public String gameName;
    private List<SubFeature> data;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<SubFeature> getData() {
        return data;
    }

    public void setData(List<SubFeature> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "gameName='" + gameName + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}

