package wang.james.attendance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by james on 7/3/15.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ItemHolder> {

    private LayoutInflater inflater;
    private Context context;
    List<String> items = Collections.emptyList();

    public DrawerAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_row, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder parent, int position) {
        ItemHolder itemHolder = parent;
        String current = items.get(position);
        itemHolder.item.setText(current);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView item;

        public ItemHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.drawer_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getPosition();
            switch (position) {
                case 0:
                    context.startActivity(new Intent(context, TakeAttendanceActivity.class));
                    break;
                case 1:
                    context.startActivity(new Intent(context, ViewAttendanceActivity.class));
                default:
                    break;
            }
        }
    }
}