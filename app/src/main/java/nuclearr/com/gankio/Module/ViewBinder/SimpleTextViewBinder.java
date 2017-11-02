package nuclearr.com.gankio.Module.ViewBinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewBinder;
import nuclearr.com.gankio.R;

public final class SimpleTextViewBinder extends ItemViewBinder<String, SimpleTextViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected SimpleTextViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.simple_text_view, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull SimpleTextViewBinder.ViewHolder holder, @NonNull String item) {
        holder.title.setText(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.simple_text_view);
        }
    }
}
