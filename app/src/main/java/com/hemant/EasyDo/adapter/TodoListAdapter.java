package com.hemant.EasyDo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hemant.EasyDo.R;
import com.hemant.EasyDo.db.TodoTask;

import java.util.List;
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoTaskViewHolder> {
    private final LayoutInflater mInflater;
    private List<TodoTask> mTasks;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onDeleteClick(TodoTask task);
        void onUndoClick(TodoTask task);
        void onCheckboxClick(TodoTask task);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public TodoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public TodoTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item,parent,false);
        return new TodoTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoTaskViewHolder holder, int position) {
        if (mTasks != null) {
            TodoTask current = mTasks.get(position);
            holder.titletv.setText(current.getTitle());
            holder.descriptiontv.setText(current.getDescription());
            updateUi(current , holder);

        }
        else {
            holder.titletv.setText("No Task");
        }
    }
    public void setTaskList(List<TodoTask> taskList) {
        mTasks = taskList;
        notifyDataSetChanged();
    }
    public void updateUi(TodoTask current, TodoTaskViewHolder holder){
        if(current.getStatus() == true){
            holder.statuscb.setChecked(true);
            holder.statuscb.setVisibility(LinearLayout.GONE);
            holder.optionView.setVisibility(LinearLayout.VISIBLE);
            holder.card.setBackgroundColor(Color.rgb(200,200,200));
            holder.titletv.setPaintFlags(holder.titletv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            holder.statuscb.setChecked(false);
            holder.statuscb.setVisibility(LinearLayout.VISIBLE);
            holder.optionView.setVisibility(LinearLayout.GONE);
            holder.card.setBackgroundColor(Color.WHITE);
            holder.titletv.setPaintFlags(holder.titletv.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else
            return 0;
    }

    public class TodoTaskViewHolder extends RecyclerView.ViewHolder {
        private final CardView card;
        private final TextView titletv;
        private final CheckBox statuscb;
        private  final ImageButton undoBtn;
        private  final ImageButton deleteBtn;
        private final LinearLayout optionView;
        private final TextView descriptiontv;
        private TodoTaskViewHolder(View itemView) {
            super(itemView);
            titletv = itemView.findViewById(R.id.titleTV);
            statuscb = itemView.findViewById(R.id.stateCb);
            undoBtn = itemView.findViewById(R.id.undoBtn);
            deleteBtn = itemView.findViewById(R.id.removeBtn);
            optionView = itemView.findViewById(R.id.optionsView);
            card = itemView.findViewById(R.id.cardView);
            descriptiontv = itemView.findViewById(R.id.descriptontv);
            undoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statuscb.setVisibility(View.VISIBLE);
                    optionView.setVisibility(View.GONE);
                    TodoTask task = mTasks.get(getAdapterPosition());
                    TodoTask update = new TodoTask(task.getId(),task.getTitle(),!task.getStatus(),task.getPriority(),task.getDescription());
                    listener.onUndoClick(update);
                    titletv.setPaintFlags(titletv.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            });
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(mTasks.get(getAdapterPosition()));
                }
            });
            statuscb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    cb.setVisibility(View.GONE);
                    optionView.setVisibility(View.VISIBLE);
                    titletv.setPaintFlags(titletv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    TodoTask task = mTasks.get(getAdapterPosition());
                    TodoTask update = new TodoTask(task.getId(),task.getTitle(),!task.getStatus(),task.getPriority(),task.getDescription());
                    listener.onCheckboxClick(update);
                }
            });
        }
    }
}
