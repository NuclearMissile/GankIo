package nuclearr.com.gankio.Module.ViewBinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewBinder;
import nuclearr.com.gankio.Bean.GanItem;
import nuclearr.com.gankio.Module.Activity.BaseActivity;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.CustomTabsUtil;
import nuclearr.com.gankio.Util.DateUtil;
import nuclearr.com.gankio.Util.ShareUtil;

public final class GanItemViewBinderForDetailView extends ItemViewBinder<GanItem, GanItemViewBinderForDetailView.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.gan_item_for_daily_detail, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GanItem item) {
        holder.title.setText(item.getDesc().replace(" ", ""));
        String publishTime = item.getPublishedAt();
        publishTime = publishTime.substring(0, publishTime.indexOf("."));
        publishTime = publishTime.replace("T", " ");
        holder.time.setText(DateUtil.getFriendlyTime(publishTime));
        holder.who.setText(item.getWho());
        holder.itemView.setOnClickListener(v -> {
            CustomTabsUtil.openUrl(BaseActivity.getInstance(), item.getUrl());
        });
        holder.itemView.setOnLongClickListener(v -> {
            ShareUtil.shareText(item.getUrl());
            return true;
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView who;
        TextView title;
        TextView time;

        ViewHolder(View itemView) {
            super(itemView);
            who = itemView.findViewById(R.id.gan_item_for_daily_detail_who);
            title = itemView.findViewById(R.id.gan_item_for_daily_detail_title);
            time = itemView.findViewById(R.id.gan_item_for_daily_detail_time);
        }
    }
}
