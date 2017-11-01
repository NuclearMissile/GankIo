package nuclearr.com.gankio.Module.Fragment.ViewBinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.ItemViewBinder;
import nuclearr.com.gankio.Bean.XianItem;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.ImageLoader.ImageLoader;

/**
 * Created by torri on 2017/11/1.
 */

public class XianViewBinder extends ItemViewBinder<XianItem, XianViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected XianViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new XianViewBinder.ViewHolder(inflater.inflate(R.layout.xian_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull XianViewBinder.ViewHolder holder, @NonNull XianItem item) {
        holder.title.setText(item.getTitle());
        holder.who.setText(item.getSource());
        holder.time.setText(item.getTime());
        ImageLoader.showImage(holder.avatar, item.getSourceAvatar());

        holder.itemView.setOnClickListener(v -> {
            // todo
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avatar;
        TextView who;
        TextView title;
        TextView time;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.xian_item_avatar);
            who = itemView.findViewById(R.id.xian_item_who);
            title = itemView.findViewById(R.id.xian_item_title);
            time = itemView.findViewById(R.id.xian_item_time);
        }
    }
}
