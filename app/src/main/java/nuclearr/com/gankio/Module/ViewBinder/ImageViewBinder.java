package nuclearr.com.gankio.Module.ViewBinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.drakeet.multitype.ItemViewBinder;
import nuclearr.com.gankio.Bean.IImageItem;
import nuclearr.com.gankio.Bean.ZhuangItem;
import nuclearr.com.gankio.Module.Activity.ImageActivity;
import nuclearr.com.gankio.Module.Activity.MainActivity;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.ImageLoader.ImageLoader;

/**
 * Created by torri on 2017/11/6.
 */

public final class ImageViewBinder extends ItemViewBinder<IImageItem, ImageViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ImageViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ImageViewBinder.ViewHolder(inflater.inflate(R.layout.image_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ImageViewBinder.ViewHolder holder, @NonNull IImageItem item) {
        holder.setIsRecyclable(false);
        ImageLoader.showImage2(holder.imageView, item.getThumbnailUrl());
        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.getInstance(), ImageActivity.class);
            intent.putExtra("url", item.getImageUrl());
            if (item instanceof ZhuangItem) {
                intent.putExtra("title", ((ZhuangItem)item).getTitle());
            }
            MainActivity.getInstance().startActivity(intent);
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item_view);
        }
    }
}
