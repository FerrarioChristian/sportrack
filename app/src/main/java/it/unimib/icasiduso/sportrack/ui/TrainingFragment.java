package it.unimib.icasiduso.sportrack.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.model.Exercises;
import it.unimib.icasiduso.sportrack.utils.JsonParserUtil;

public class TrainingFragment extends Fragment {

    private static final String TAG = TrainingFragment.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button1 = view.findViewById(R.id.buttonGroup1);
        button1.setOnClickListener( v -> {
            JsonParserUtil jsonParser = new JsonParserUtil(requireActivity().getApplication());

            Exercises exercises;
            try {
                exercises = jsonParser.parseFromFileWithGSON("biceps.json");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Log.d(TAG, exercises.toString());

        });

        Button button2 = view.findViewById(R.id.buttonGroup2);
        button2.setOnClickListener( v -> {
            JsonParserUtil jsonParser = new JsonParserUtil(requireActivity().getApplication());

            Exercises exercises;
            try {
                exercises = jsonParser.parseFromFileWithGSON("abdominals.json");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Log.d(TAG, exercises.toString());

        });


    }

}