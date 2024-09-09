package it.unimib.icasiduso.sportrack.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;

public class WorkoutExerciseRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutExerciseRecyclerViewAdapter.WorkoutExerciseViewHolder> {
    private final OnItemClickListener onItemClickListener;
    private final ExerciseViewModel exerciseViewModel;
    private final LifecycleOwner lifecycleOwner;
    private List<WorkoutExercise> workoutExercises;


    public WorkoutExerciseRecyclerViewAdapter(OnItemClickListener onItemClickListener,
                                              LifecycleOwner lifecycleOwner,
                                              ExerciseViewModel exerciseViewModel) {
        this.onItemClickListener = onItemClickListener;
        this.lifecycleOwner = lifecycleOwner;
        this.exerciseViewModel = exerciseViewModel;
    }

    public void setWorkoutExercises(List<WorkoutExercise> workoutExercises) {
        this.workoutExercises = workoutExercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkoutExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_exercise_item, parent, false);
        return new WorkoutExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutExerciseViewHolder holder, int position) {
        holder.bind(workoutExercises.get(position));
    }

    @Override
    public int getItemCount() {
        if (workoutExercises == null) return 0;
        return workoutExercises.size();
    }

    public interface OnItemClickListener {
        void onExerciseClick(WorkoutExercise workoutExercise);
    }

    public class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView exerciseName;
        private final TextView exerciseSeries;
        private final TextView exerciseRepetitions;

        public WorkoutExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseSeries = itemView.findViewById(R.id.exerciseSeries);
            exerciseRepetitions = itemView.findViewById(R.id.exerciseRepetitions);
            itemView.setOnClickListener(this);
        }


        public void bind(WorkoutExercise workoutExercise) {
            exerciseViewModel.getExerciseById(workoutExercise.getExerciseId())
                    .observe(lifecycleOwner, exercise -> {
                        //TODO Parsare nome esercizio
                        exerciseName.setText(exercise.getName());
                    });

            exerciseSeries.setText(App.getRes()
                    .getString(R.string.series, workoutExercise.getSeries()));
            exerciseRepetitions.setText(App.getRes()
                    .getString(R.string.repetitions, workoutExercise.getRepetitions()));

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onExerciseClick(workoutExercises.get(getBindingAdapterPosition()));
        }

    }
}

