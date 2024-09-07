package it.unimib.icasiduso.sportrack.ui;

import static java.lang.Integer.parseInt;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.databinding.FragmentHomepageBinding;

public class HomepageFragment extends Fragment {
    private static final String TAG = HomepageFragment.class.getSimpleName();

    private FragmentHomepageBinding binding;

    public HomepageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayout heatmapGrid = binding.heatmapGrid;

        // Example data representing activity levels
        int[] activityData = {1, 5, 3, 7, 0, 2, 4, 8, 1, 3, 4, 6, 1, 9, 3, 0, 2, 5, 7, 3, 2, 4, 6, 0, 8, 1, 3, 4, 9, 3, 1, 5, 3, 7, 0, 2, 4, 8, 1, 3, 4, 6, 1, 9, 3, 0, 2, 5, 7, 3, 2, 4, 6, 0, 8, 1, 3, 4, 9, 3, 1, 5, 3, 7, 0, 2, 4, 8, 1, 3, 4, 6, 1, 9, 3, 0, 2, 5, 7, 3, 2, 4, 6, 0, 8, 1, 3, 4, 9, 3, 1, 5, 3, 7, 0, 2, 4, 8, 1, 3, 4, 6, 1, 9, 3, 0, 2, 5, 7, 3, 2, 4, 6, 0, 8, 1, 3, 4, 9, 3};

        for (int activityLevel : activityData) {

            View view2 = new View(getContext());

            int color;
            switch (activityLevel) {
                case 0:
                    color = Color.parseColor("#ebedf0");
                    break;
                case 1:
                    color = Color.parseColor("#c6e48b");
                    break;
                case 2:
                    color = Color.parseColor("#7bc96f");
                    break;
                case 3:
                    color = Color.parseColor("#239a3b");
                    break;
                default:
                    color = Color.parseColor("#196127");
                    break;
            }

            view2.setBackgroundColor(color);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 50;
            params.height = 50;
            params.setMargins(4, 4, 4, 4);

            heatmapGrid.addView(view2, params);
        }

    }
}