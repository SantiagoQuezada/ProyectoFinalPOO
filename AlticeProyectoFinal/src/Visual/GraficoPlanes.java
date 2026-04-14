package Visual;

import Logico.Altice;
import Logico.Cliente;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class GraficoPlanes extends JDialog {

    public GraficoPlanes() {
        setTitle("Estadísticas - Planes Más Usados");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        DefaultPieDataset dataset = new DefaultPieDataset();
        HashMap<String, Integer> conteoPlanes = new HashMap<>();

        for (Cliente c : Altice.getInstance().getClientes()) {
            if (c.getPlan() != null && c.getEstado().equals("Activo")) {
                String nombrePlan = c.getPlan().getNombre();
                conteoPlanes.put(nombrePlan, conteoPlanes.getOrDefault(nombrePlan, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entrada : conteoPlanes.entrySet()) {
            dataset.setValue(entrada.getKey() + " (" + entrada.getValue() + " clientes)", entrada.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribución de Planes Activos",
                dataset,
                true,
                true,
                false
        );

        chart.setBackgroundPaint(new Color(245, 247, 250));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 550));
        add(chartPanel, BorderLayout.CENTER);
    }
}