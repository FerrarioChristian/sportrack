package it.unimib.icasiduso.sportrack.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.model.Exercise;
import it.unimib.icasiduso.sportrack.model.Exercises;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExerciseViewHolder> {
    List<Exercise> exercises; //TODO
    private final Application application;
    private final OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onExerciseClick(Exercise exercise);
    }

    public ExerciseRecyclerViewAdapter(List<Exercise> exercises, Application application, OnItemClickListener onItemClickListener) {
        this.exercises = exercises;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        holder.bind(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        if (exercises == null)
            return 0;
        return exercises.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewExerciseName;
        private final TextView textViewExerciseDifficulty;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            textViewExerciseDifficulty = itemView.findViewById(R.id.textViewExerciseDifficulty);
            itemView.setOnClickListener(this);
        }

        public void bind(Exercise exercise) {
            textViewExerciseName.setText(exercise.getName());
            textViewExerciseDifficulty.setText(application.getApplicationContext().getResources().getString(R.string.difficulty) + exercise.getDifficulty());
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onExerciseClick(exercises.get(getAdapterPosition()));
        }
    }

}
