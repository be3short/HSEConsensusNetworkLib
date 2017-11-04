package edu.ucsc;

import edu.cross.ucsc.hse.core.chart.HybridChart;
import edu.cross.ucsc.hse.core.chart.LabelType;
import edu.ucsc.cross.hse.consensus.sync.SyncNetwork;
import edu.ucsc.cross.hse.core.container.EnvironmentData;
import edu.ucsc.cross.hse.core.environment.Environment;
import edu.ucsc.cross.hse.core.monitor.DataMonitor;
import edu.ucsc.cross.hse.core.task.TaskManager;
import edu.ucsc.cross.hse.model.consensus.async.AsyncNetwork;
import edu.ucsc.cross.hse.models.network.syncconsensus.Network;
import java.awt.Font;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * This is an example of what an empty task execution class looks like. You can copy this code to create your own
 * version.
 */
public class TaskExecutor extends TaskManager
{

	// Everything within the taskQueue() method will be executed
	@Override
	public void taskQueue()
	{
		// Your tasks go here
		// runSyncNetwork();
		runASyncAgentNetwork();
		// openEnvironmentAndPlot();
		// SampleAndHoldTasks.signalGeneratorSampleAndHoldSimulation(20.0, .3, .1);
	}

	public static Environment getConfiguredEnvironment()
	{
		Environment env = new Environment();
		env.getSettings().getLogSettings().printInfo = true;
		env.getSettings().getLogSettings().printDebug = true;
		env.getSettings().getLogSettings().printError = true;
		env.getSettings().getLogSettings().printIntegratorExceptions = true;
		env.getSettings().getOutputSettings().dataPointInterval = .3;
		env.getSettings().getOutputSettings().appendFilesWithTime = false;
		env.getSettings().getOutputSettings().saveChartsToFile = false;
		env.getSettings().getOutputSettings().saveEnvironmentToFile = true;
		env.getSettings().getOutputSettings().saveConfigurationToFile = false;
		// env.getSettings().getOutputSettings().chartFileFormat = ImageFormat.EPS;
		return env;
	}

	public void runSyncNetwork()
	{
		Network net = Network.generateRandomNetwork(20, .4, .1);
		Environment env = getConfiguredEnvironment();
		env.add(net);
		env.add(statesChart());
		env.start(20.0, 1000000);
		// statesAndTimerChart().plot(env);
		// statesChart().plot(env);
	}

	public void runSyncAgentNetwork()
	{

		Environment env = getConfiguredEnvironment();
		SyncNetwork.generateRandomNetwork(env, 5, .4, .1);

		// env.add(statesChart());
		env.start(100.0, 1000000);
		// statesAndTimerChart().plot(env);
		// statesChart().plot(env);
	}

	public void runASyncAgentNetwork()
	{

		Environment env = getConfiguredEnvironment();
		env.add(AsyncNetwork.generateRandomNetwork(15, .4, .1));

		// env.add(statesChart());
		env.start(20.0, 1000000);
		statesAndTimerChart().plot(env);
		// statesAndTimerChart().plot(env);
		statesChart().plot(env);
	}

	public void openEnvironmentAndPlot()
	{
		File envFile = new FileChooser().showOpenDialog(new Stage());
		Environment env = Environment.loadFile(envFile);
		File datFile = new FileChooser().showOpenDialog(new Stage());
		EnvironmentData dat = DataMonitor.getCSVData(datFile);
		env.getData().load(dat.getStoreTimes(), dat.getGlobalStateData());
		// env.getOutputs().generateOutputs(env, true);
		// env.add(xyCombination());

		// statesAndTimerChart().plot(env);
		statesChart().plot(env);
	}

	public static HybridChart statesAndTimerChart()
	{
		// Create a new plot configuration with specified width (600.0) and height (600.0)
		HybridChart plot = new HybridChart(1000.0, 800.0);
		// plot.
		// Set layout to generate two horizontal plots with plot 0 on top and plot 1 on the bottom
		plot.setLayout(new Integer[][]
		{
				{ 1, 1, 1, 2, 2 },
				{ 0, 0, 0, 2, 2 } });

		// Select data to display
		// * selections should be a string that matches the variable name of the data to be selected
		// * null is used to select time as the x axis values
		plot.sub(0).setAxisSelections(null, "stateValue");
		plot.sub(1).setAxisSelections(null, "controllerValue");
		plot.sub(2).setAxisSelections(null, "eventTimer");

		// Select axis labels
		// * null is used to remove an axis label completely
		// Chart.EMPTY is used to remove an axis label but keep the space so the sub chart stays alligned with grid
		plot.sub(0).setAxisLabels("Time (sec)", "X Value");
		plot.sub(1).setAxisLabels("Time (sec)", "Y Value");
		plot.sub(2).setAxisLabels(null, null);

		// Specify legend visibility
		plot.sub(0).setDisplayLegend(false);
		plot.sub(1).setDisplayLegend(false);
		plot.sub(2).setDisplayLegend(false);

		// Specify overall title for the plot
		// * null is used to indicate no sub plot title
		// * there are no sub plot titles by default so following lines can be ommitted for no sub plot titles
		plot.sub(0).setTitle(null);
		plot.sub(1).setTitle(null);
		plot.sub(2).setTitle("X Value vs Y Value");

		// Specify overall title for the plot
		// * null is used to indicate no main title
		// * there is no main title by default so following line can be ommitted if no main title is desired
		plot.addMainTitle("Signal Generator", null);

		plot.editFonts(LabelType.TITLE).set(new Font("Tahoma", Font.ITALIC, 18));
		plot.editFonts(LabelType.RANGE_AXIS_LABEL).set(new Font("Tahoma", Font.BOLD, 14));
		plot.editFonts(LabelType.DOMAIN_AXIS_LABEL).set(new Font("Tahoma", Font.BOLD, 14));
		plot.editFonts(LabelType.RANGE_AXIS_TICK_LABEL).set(new Font("Tahoma", Font.PLAIN, 12));
		plot.editFonts(LabelType.DOMAIN_AXIS_TICK_LABEL).set(new Font("Tahoma", Font.PLAIN, 12));
		plot.editFonts(LabelType.LEGEND_ITEM).set(new Font("Tahoma", Font.PLAIN, 10));
		return plot;
	}

	public static HybridChart statesChart()
	{
		// Create a new plot configuration with specified width (600.0) and height (600.0)
		HybridChart plot = new HybridChart(1000.0, 800.0);
		// plot.
		// Set layout to generate two horizontal plots with plot 0 on top and plot 1 on the bottom
		plot.setLayout(new Integer[][]
		{
				{ 1, 1, 1, 2 },
				{ 0, 0, 0, 2 } });

		// Select data to display
		// * selections should be a string that matches the variable name of the data to be selected
		// * null is used to select time as the x axis values
		plot.sub(0).setAxisSelections(null, "stateValue");
		plot.sub(1).setAxisSelections(null, "controllerValue");
		plot.sub(2).setAxisSelections(null, "controllerValue");

		// Select axis labels
		// * null is used to remove an axis label completely
		// Chart.EMPTY is used to remove an axis label but keep the space so the sub chart stays alligned with grid
		plot.sub(0).setAxisLabels("Time (sec)", "X Value");
		plot.sub(1).setAxisLabels("Time (sec)", "Y Value");
		plot.sub(2).setAxisLabels(null, null);

		// Specify legend visibility
		plot.sub(0).setDisplayLegend(false);
		plot.sub(1).setDisplayLegend(false);
		plot.sub(2).setDisplayLegend(true);

		// Specify overall title for the plot
		// * null is used to indicate no sub plot title
		// * there are no sub plot titles by default so following lines can be ommitted for no sub plot titles
		plot.sub(0).setTitle(null);
		plot.sub(1).setTitle(null);
		plot.sub(2).setTitle(null);

		// Specify overall title for the plot
		// * null is used to indicate no main title
		// * there is no main title by default so following line can be ommitted if no main title is desired
		plot.addMainTitle("Consensus Network", null);

		plot.editFonts(LabelType.TITLE).set(new Font("Tahoma", Font.ITALIC, 18));
		plot.editFonts(LabelType.RANGE_AXIS_LABEL).set(new Font("Tahoma", Font.BOLD, 14));
		plot.editFonts(LabelType.DOMAIN_AXIS_LABEL).set(new Font("Tahoma", Font.BOLD, 14));
		plot.editFonts(LabelType.RANGE_AXIS_TICK_LABEL).set(new Font("Tahoma", Font.PLAIN, 12));
		plot.editFonts(LabelType.DOMAIN_AXIS_TICK_LABEL).set(new Font("Tahoma", Font.PLAIN, 12));
		plot.editFonts(LabelType.LEGEND_ITEM).set(new Font("Tahoma", Font.PLAIN, 10));
		return plot;
	}

	public static void main(String args[])
	{
		launch();
	}

}
