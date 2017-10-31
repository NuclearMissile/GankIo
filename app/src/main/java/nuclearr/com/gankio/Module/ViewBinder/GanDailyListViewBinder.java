package nuclearr.com.gankio.Module.ViewBinder;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewBinder;
import nuclearr.com.gankio.Bean.GanDailyListItem;
import nuclearr.com.gankio.R;
import nuclearr.com.gankio.Util.DateUtil;
import nuclearr.com.gankio.Util.ImageLoader.ImageLoader;

public final class GanDailyListViewBinder extends ItemViewBinder<GanDailyListItem, GanDailyListViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected GanDailyListViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.daily_list_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GanDailyListItem item) {
        boolean isToday = DateUtil.isToday(DateUtil.parseDate(item.getPublishDate()));
        // holder.imageView.setTag(R.id.image_id, item.getImageUrl());
        holder.setIsRecyclable(false);
        ImageLoader.showImage(holder.imageView, item.getImageUrl());
        holder.imageView.setColorFilter(Color.parseColor("#5e000000"));
        holder.dateTextView.setText(isToday ? "#Today" : "#" + item.getPublishDate());
        holder.descTextView.setText(item.getTitle().replace("今日力推：", ""));
        holder.itemView.setOnClickListener(v -> {
            // todo->start new activity
        });
        holder.itemView.setOnTouchListener(new OnTapListener(holder));
    }

    static class OnTapListener implements View.OnTouchListener {
        ViewHolder holder;

        OnTapListener(ViewHolder viewHolder) {
            holder = viewHolder;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    holder.imageView.setColorFilter(null);
                    hide(holder.dateTextView);
                    hide(holder.descTextView);
                    View parent = (View) holder.itemView.getParent();
                    if (parent != null)
                        parent.setOnTouchListener(this);
                    break;
                case MotionEvent.ACTION_UP:
                    holder.imageView.setColorFilter(Color.parseColor("#5e000000"));
                    show(holder.descTextView);
                    show(holder.dateTextView);
                    break;
            }
            return false;
        }

        void show(View view) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
            alpha.setDuration(300);
            alpha.start();
        }

        void hide(View view) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
            alpha.setDuration(300);
            alpha.start();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView dateTextView;
        TextView descTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.daily_list_image_view);
            dateTextView = itemView.findViewById(R.id.daily_list_item_date);
            descTextView = itemView.findViewById(R.id.daily_list_item_desc);
        }
    }
}
