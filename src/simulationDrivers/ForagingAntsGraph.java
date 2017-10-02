package simulationDrivers;

import cellManager.Grid;

import javafx.scene.chart.XYChart;

/**
 * @author Diane Hu Subclass of graph superclass creating a foraging ants graph.
 *         Used in the simulation subclass for foraging ants to instantiate a
 *         foraging ants graph. Depends on javafx chart imports.
 */
public class ForagingAntsGraph extends Graph {

	/**
	 * @param newGrid
	 * 
	 *            Constructor using superclass constructor
	 */
	public ForagingAntsGraph(Grid newGrid) {
		super(newGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#update()
	 * 
	 * Updates the simulation appropriate series of percent ants and percent empty.
	 */
	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentAnt()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentEmpty()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#addData()
	 * 
	 * Adds the simulation appropriate series to the linechart.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#setNames()
	 * 
	 * Sets the series name appropriately for this simulation type.
	 */
	@Override
	protected void setNames() {
		series1.setName("Percent ant groups");
		series2.setName("Percent empty");
	}
}
