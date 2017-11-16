package edu.ucsc;

import edu.cross.ucsc.hse.core.chart.ChartConfiguration;
import edu.ucsc.cross.hse.core.environment.Environment;
import edu.ucsc.cross.hse.core.task.TaskManager;
import edu.ucsc.cross.hse.model.consensus.async.consensus.AsyncConsistencyNetwork;
import edu.ucsc.cross.hse.model.consensus.async.consensus.active.SeqConsistencyNetwork;
import edu.ucsc.cross.hse.model.consensus.asynchronous.AsyncNetworkSystem;
import edu.ucsc.cross.hse.model.consensus.synchronous.SyncNetworkSystem;
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
		// runASyncAgentNetwork();
		// runASyncConsensusNetwork();
		runASyncAgentNetwork();
		// runASyncSeqConsensusNetwork();
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
		env.getSettings().getOutputSettings().appendFilesWithNumericDate = false;
		env.getSettings().getOutputSettings().saveChartsToFile = false;
		env.getSettings().getOutputSettings().saveEnvironmentToFile = true;
		env.getSettings().getOutputSettings().saveConfigurationToFile = false;
		// env.getSettings().getOutputSettings().chartFileFormat = ImageFormat.EPS;
		return env;
	}

	public void runSyncNetwork()
	{
		SyncNetworkSystem net = SyncNetworkSystem.generateRandomNetwork(20, .4, .1);
		Environment env = getConfiguredEnvironment();
		env.add(net);
		env.add(statesChart());
		env.start(20.0, 1000000);
		// statesAndTimerChart().plot(env);
		// statesChart().plot(env);
	}

	public void runASyncAgentNetwork()
	{

		Environment env = getConfiguredEnvironment();
		env.add(AsyncNetworkSystem.generateRandomNetwork(115, .4, .1, .5, 1.0));

		// env.add(statesChart());
		env.start(18.0, 100000);
		statesAndTimerChart().createChart(env);
		// statesAndTimerChart().plot(env);
		statesChart().createChart(env);
	}

	public void runASyncConsensusNetwork()
	{

		Environment env = getConfiguredEnvironment();
		env.add(AsyncConsistencyNetwork.generateRandomNetwork(15, .4, .1, .8, 1.8, .02, .05));

		// env.add(statesChart());
		env.start(50.0, 100000);
		statesAndTimerChart().createChart(env);
		// statesAndTimerChart().plot(env);
		statesChart().createChart(env);
	}

	public void runASyncSeqConsensusNetwork()
	{

		Environment env = getConfiguredEnvironment();
		env.add(SeqConsistencyNetwork.generateRandomNetwork(15, .4, .1, .8, 1.8, .02, .05));

		// env.add(statesChart());
		env.start(50.0, 100000);
		statesAndTimerChart().createChart(env);
		// statesAndTimerChart().plot(env);
		statesChart().createChart(env);
	}

	public void openEnvironmentAndPlot()
	{
		File envFile = new FileChooser().showOpenDialog(new Stage());
		Environment env = Environment.createEnvironment(envFile);
		File datFile = new FileChooser().showOpenDialog(new Stage());
		// EnvironmentData dat = DataMonitor.getCSVData(datFile);
		// env.getData().load(dat.getStoreTimes(), dat.getGlobalStateData());
		// env.getOutputs().generateOutputs(env, true);
		// env.add(xyCombination());

		// statesAndTimerChart().plot(env);
		statesChart().createChart(env);
	}

	public static ChartConfiguration statesAndTimerChart()
	{
		// Create a new plot configuration with specified width (600.0) and height (600.0)
		ChartConfiguration plot = new ChartConfiguration(1000.0, 800.0);
		// plot.
		// Set layout to generate two horizontal plots with plot 0 on top and plot 1 on the bottom
		plot.setLayout(new Integer[][]
		{
				{ 1, 1, 1, 2, 2 },
				{ 0, 0, 0, 2, 2 } });

		// Select data to display
		// * selections should be a string that matches the variable name of the data to be selected
		// * null is used to select time as the x axis values
		plot.chartProperties(0).setAxisSelections(null, "stateValue");
		plot.chartProperties(1).setAxisSelections(null, "controllerValue");
		plot.chartProperties(2).setAxisSelections(null, "eventTimer");

		// Select axis labels
		// * null is used to remove an axis label completely
		// Chart.EMPTY is used to remove an axis label but keep the space so the sub chart stays alligned with grid
		plot.chartProperties(0).setAxisLabels("Time (sec)", "X Value");
		plot.chartProperties(1).setAxisLabels("Time (sec)", "Y Value");
		plot.chartProperties(2).setAxisLabels(null, null);

		// Specify legend visibility
		plot.chartProperties(0).setDisplayLegend(false);
		plot.chartProperties(1).setDisplayLegend(false);
		plot.chartProperties(2).setDisplayLegend(false);

		// Specify overall title for the plot
		// * null is used to indicate no sub plot title
		// * there are no sub plot titles by default so following lines can be ommitted for no sub plot titles
		plot.chartProperties(0).setTitle(null);
		plot.chartProperties(1).setTitle(null);
		plot.chartProperties(2).setTitle("X Value vs Y Value");

		// Specify overall title for the plot
		// * null is used to indicate no main title
		// * there is no main title by default so following line can be ommitted if no main title is desired
		plot.addMainTitle("Signal Generator", null);

		return plot;
	}

	public static ChartConfiguration statesChart()
	{
		// Create a new plot configuration with specified width (600.0) and height (600.0)
		ChartConfiguration plot = new ChartConfiguration(1000.0, 800.0);
		// plot.
		// Set layout to generate two horizontal plots with plot 0 on top and plot 1 on the bottom
		plot.setLayout(new Integer[][]
		{
				{ 1, 1, 1, 2 },
				{ 0, 0, 0, 2 } });

		// Select data to display
		// * selections should be a string that matches the variable name of the data to be selected
		// * null is used to select time as the x axis values
		plot.chartProperties(0).setAxisSelections(null, "stateValue");
		plot.chartProperties(1).setAxisSelections(null, "controllerValue");
		plot.chartProperties(2).setAxisSelections(null, "controllerValue");

		// Select axis labels
		// * null is used to remove an axis label completely
		// Chart.EMPTY is used to remove an axis label but keep the space so the sub chart stays alligned with grid
		plot.chartProperties(0).setAxisLabels("Time (sec)", "X Value");
		plot.chartProperties(1).setAxisLabels("Time (sec)", "Y Value");
		plot.chartProperties(2).setAxisLabels(null, null);

		// Specify legend visibility
		plot.chartProperties(0).setDisplayLegend(false);
		plot.chartProperties(1).setDisplayLegend(false);
		plot.chartProperties(2).setDisplayLegend(true);

		// Specify overall title for the plot
		// * null is used to indicate no sub plot title
		// * there are no sub plot titles by default so following lines can be ommitted for no sub plot titles
		plot.chartProperties(0).setTitle(null);
		plot.chartProperties(1).setTitle(null);
		plot.chartProperties(2).setTitle(null);

		// Specify overall title for the plot
		// * null is used to indicate no main title
		// * there is no main title by default so following line can be ommitted if no main title is desired
		plot.addMainTitle("Consensus Network", null);

		return plot;
	}

	public static void main(String args[])
	{
		launch();
	}

}
