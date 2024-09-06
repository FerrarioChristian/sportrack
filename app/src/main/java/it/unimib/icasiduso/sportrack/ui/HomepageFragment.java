package it.unimib.icasiduso.sportrack.ui;

import static java.lang.Integer.parseInt;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

    private List<DataPoint> weightDataList;
    private int i;
    private double sumPeso;
    private LineGraphSeries<DataPoint> mediaSeries;

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

        EditText inputPeso = view.findViewById(R.id.Weight);
        TextView tvMediaPeso = view.findViewById(R.id.tvMediaPeso);
        TextView tvFeedback = view.findViewById(R.id.tvFeedback);

        weightDataList = new ArrayList<>();

        // Inizializza la lista dei dati
        weightDataList = new ArrayList<>();

        // Inizializza la serie della media
        mediaSeries = new LineGraphSeries<>();
        mediaSeries.setColor(Color.RED);
        mediaSeries.setTitle("Media"); // Imposta il titolo per la legenda

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;

                // Aggiorna la lista dei dati con il nuovo punto
                int nuovoPeso = parseInt(inputPeso.getText().toString());
                weightDataList.add(new DataPoint(i, nuovoPeso));

                // Aggiorna la somma dei pesi
                sumPeso += nuovoPeso;


                //creazione del GraphView
                GraphView graph = binding.graph;
                // Imposta i limiti della vista del grafico
                graph.getViewport().setXAxisBoundsManual(false);
                graph.setScaleX(1);
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(200);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(7); // i è il contatore dei dati


                // Abilita lo scorrimento orizzontale
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScrollableY(false); // Se vuoi anche lo scorrimento verticale


                // Crea una serie di dati a barre per i pesi (giallo con bordo nero)
                BarGraphSeries<DataPoint> pesiSeries = new BarGraphSeries<>(weightDataList.toArray(
                        new DataPoint[0]));
                pesiSeries.setColor(Color.TRANSPARENT); // Imposta il colore interno
                pesiSeries.setSpacing(50); // Imposta la larghezza dello spazio tra le barre
                pesiSeries.setDrawValuesOnTop(true);
                pesiSeries.setValuesOnTopColor(Color.BLACK); // Imposta il colore del testo sopra le barre
                pesiSeries.setValuesOnTopSize(24); // Imposta la dimensione del testo sopra le barre


                // Aggiorna la serie della media con l'ultimo valore
                double media = sumPeso / i;
                mediaSeries.appendData(new DataPoint(i, media), true, i);

                //mostra la media del peso
                tvMediaPeso.setText("Media Peso: " + String.format("%.2f", media));
                // Fornisci feedback per ogni dato
                if (i > 0) {
                    DataPoint ultimoDato = weightDataList.get(i - 1);
                    double pesoUltimoDato = ultimoDato.getY();

                    if (pesoUltimoDato > media) {
                        tvFeedback.setText("Coraggio, impegnati di più!");
                    } else {
                        tvFeedback.setText("Continua così!");
                    }
                } else {
                    // Gestisci il caso in cui non ci sono dati inseriti
                    tvFeedback.setText("");
                }
                // Cancella tutte le serie precedenti e aggiunge le nuove serie al grafico
                graph.removeAllSeries();
                graph.addSeries(pesiSeries);
                graph.addSeries(mediaSeries);

                // Aggiunge la legenda
                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                // Imposta lo sfondo arancio
                graph.setBackgroundColor(Color.parseColor("#60FFA500")); // Arancio

                // Aggiunge il cliccato listener per visualizzare i valori
                graph.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        float touchX = motionEvent.getX();
                        float touchY = motionEvent.getY();

                        // Trasforma le coordinate dello schermo in coordinate del grafico
                        double minX = graph.getViewport().getMinX(false);
                        double maxX = graph.getViewport().getMaxX(false);
                        double x = minX + (touchX / view.getWidth()) * (maxX - minX);

                        double minY = graph.getViewport().getMinY(false);
                        double maxY = graph.getViewport().getMaxY(false);
                        double y = minY + (1 - touchY / view.getHeight()) * (maxY - minY);

                        // Ora x e y contengono le coordinate del clic
                        handleTouch(x, y);
                        return false;
                    }
                });
            }
        });

    }

    private void handleTouch(double x, double y) {
        // Puoi utilizzare x e y per determinare il punto cliccato e visualizzare i dati corrispondenti
        // Ad esempio, puoi trovare la barra più vicina a x e visualizzare il suo valore
        // E puoi anche visualizzare il valore della media in quel punto
        // Esempio:
        double barWidth = 1.0; // Larghezza della barra
        for (DataPoint dataPoint : weightDataList) {
            double barCenter = dataPoint.getX();
            if (Math.abs(x - barCenter) <= barWidth / 2) {
                // Questo è il punto cliccato, mostra i valori
                double pesoBarra = dataPoint.getY();
                double media = sumPeso / i;
                String message = "Valore della Barra: " + pesoBarra + "\nUltima Media: " + media;
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}