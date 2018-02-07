package org.usfirst.frc.team1559.util;

import java.awt.Color;

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

	private static XYSeries series1 = new XYSeries("Value");
	private static final long serialVersionUID = 1L;
	private static Graph graph;

	public Graph(String title) {
		super(title);
		final XYDataset dataset = createDataset(0);
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
		graph = new Graph("Dynamic Graph");
		graph.pack();
		graph.setVisible(true);
	}

	private static XYDataset createDataset(int i) {
		series1.add(i, i);
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		return dataset;
	}

	private static JFreeChart createChart(final XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart("Super-Duper Awesome Chart", "Time", "Value", dataset,
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
	
	public static void updateValue(int value) {
		graph.setContentPane(new ChartPanel(createChart(createDataset(value))));
		graph.validate();
		graph.repaint();
	}

}