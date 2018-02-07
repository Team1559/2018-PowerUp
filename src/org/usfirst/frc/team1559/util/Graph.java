package org.usfirst.frc.team1559.util;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * Based on https://github.com/jfree/jfreechart
 * 
 * @author Victor Robotics Team 1559, Software (Evan Gartley)
 * @author https://github.com/jfree/jfreechart
 */
public class Graph extends ApplicationFrame {

	private static ArrayList<XYSeries> series = new ArrayList<XYSeries>();
	private static final long serialVersionUID = 1L;
	private static Graph graph;
	private static long time = 0L;

	public Graph(String title) {
		super(title);
	}
	
	public static void init(String... titles) {
		graph = new Graph("Multi-Value Graph");
		graph.pack();
		graph.setVisible(true);
		for (String title : titles)
			series.add(new XYSeries(title));
		updateValue(0, 0);
	}

	private static XYDataset createDataset(int i, int x, long y) {
		series.get(i).add(x, y);
		final XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeries s : series)
			dataset.addSeries(s);
		return dataset;
	}

	private static JFreeChart createChart(final XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart("Multi-Value Graph", "Time", "Value", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return chart;
	}
	
	public static void updateValue(int i, int value) {
		time++;
		graph.setContentPane(new ChartPanel(createChart(createDataset(i, value, time))));
		graph.validate();
		graph.repaint();
	}

}