package nuclearr.com.gankio.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.Contract;

import java.util.List;


public final class GanItem implements Parcelable {

    public static final Creator<GanItem> CREATOR = new Creator<GanItem>() {
        @Override
        public GanItem createFromParcel(Parcel in) {
            return new GanItem(in);
        }

        @Override
        public GanItem[] newArray(int size) {
            return new GanItem[size];
        }
    };
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    private GanItem(Parcel in) {
        _id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
        images = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeBooleanArray(new boolean[]{used});
        dest.writeString(who);
        dest.writeStringArray((String[]) images.toArray());
    }

    public String getPublishDate() {
        return publishedAt.substring(0, publishedAt.indexOf("T"));
    }

    @Contract(pure = true)
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Contract(pure = true)
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Contract(pure = true)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Contract(pure = true)
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Contract(pure = true)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Contract(pure = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Contract(pure = true)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Contract(pure = true)
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Contract(pure = true)
    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Contract(pure = true)
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
