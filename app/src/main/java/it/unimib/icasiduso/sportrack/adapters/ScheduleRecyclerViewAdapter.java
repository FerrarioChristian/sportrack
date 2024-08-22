package it.unimib.icasiduso.sportrack.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleViewHolder> {
    List<Schedule> scheduleList;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onScheduleClick(Schedule schedule);
    }

    public ScheduleRecyclerViewAdapter(List<Schedule> scheduleList, OnItemClickListener onItemClickListener){
        this.scheduleList = scheduleList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ScheduleRecyclerViewAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ScheduleRecyclerViewAdapter.ScheduleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ScheduleRecyclerViewAdapter.ScheduleViewHolder holder, int position) {
        holder.bind(scheduleList.get(position));
    }

    @Override
    public int getItemCount() {
        if (scheduleList == null)
            return 0;
        return scheduleList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewExerciseName;
        private final TextView textViewExerciseDifficulty;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            textViewExerciseDifficulty = itemView.findViewById(R.id.textViewExerciseDifficulty);
            itemView.setOnClickListener(this);
        }

        public void bind(Schedule schedule) {
            textViewExerciseName.setText(schedule.getName());
            textViewExerciseDifficulty.setText(App.getRes().getString(R.string.difficulty, schedule.getDifficulty()));
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onScheduleClick(scheduleList.get(getAdapterPosition()));
        }
    }

}

