package nuclearr.com.gankio.Bean;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Contract;

import java.util.List;

public final class GanDailyDetailItem {

    private List<GanItem> Android;
    private List<GanItem> iOS;
    @SerializedName("休息视频")
    private List<GanItem> Video;
    @SerializedName("拓展资源")
    private List<GanItem> Extend;
    @SerializedName("瞎推荐")
    private List<GanItem> Recommend;
    @SerializedName("福利")
    private List<GanItem> Picture;
    @SerializedName("前端")
    private List<GanItem> Frontend;
    private List<String> category;

    @Contract(pure = true)
    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    @Contract(pure = true)
    public List<GanItem> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GanItem> android) {
        Android = android;
    }

    @Contract(pure = true)
    public List<GanItem> getiOS() {
        return iOS;
    }

    public void setiOS(List<GanItem> iOS) {
        this.iOS = iOS;
    }

    @Contract(pure = true)
    public List<GanItem> getVideo() {
        return Video;
    }

    public void setVideo(List<GanItem> video) {
        Video = video;
    }

    @Contract(pure = true)
    public List<GanItem> getExtend() {
        return Extend;
    }

    public void setExtend(List<GanItem> extend) {
        Extend = extend;
    }

    @Contract(pure = true)
    public List<GanItem> getRecommend() {
        return Recommend;
    }

    public void setRecommend(List<GanItem> recommend) {
        Recommend = recommend;
    }

    @Contract(pure = true)
    public List<GanItem> getPicture() {
        return Picture;
    }

    public void setPicture(List<GanItem> picture) {
        Picture = picture;
    }

    @Contract(pure = true)
    public List<GanItem> getFrontend() {
        return Frontend;
    }

    public void setFrontend(List<GanItem> frontend) {
        Frontend = frontend;
    }

}
