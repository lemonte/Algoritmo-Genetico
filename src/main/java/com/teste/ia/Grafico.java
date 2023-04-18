package com.teste.ia;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.LinkedList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Grafico extends JFrame{
   
    public Grafico() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Mochila");
        setSize(950, 700);
        setLocationRelativeTo(null);

    }
    
    public void criarGrafico(LinkedList<Double> dados){
        var test = new XYSeries("Dados");
        int x = 0;
        
        for (Double ind : dados){
            test.add(x, ind);
            x++;
        }

        var dataset = new XYSeriesCollection();
        dataset.addSeries(test);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Mochila",
                "Indivídos",
                "Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);       
        
        ChartFrame frame1 = new ChartFrame("Gráfico de linhas", chart);
        
        frame1.setVisible(true);
        frame1.setSize(1300, 800);
        
    }


}
