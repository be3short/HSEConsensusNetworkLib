package edu.ucsc.cross.hse.model.consensus;

import java.io.File;

import edu.cross.ucsc.hse.core.chart.ChartConfiguration;
import edu.ucsc.cross.hse.core.environment.Environment;
import edu.ucsc.cross.hse.core.task.TaskManager;
import edu.ucsc.cross.hse.model.consensus.graph.GraphGenerator;
import edu.ucsc.cross.hse.model.consensus.graph.NetworkGraph;
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

	public void runASyncAgentNetwork()
	{

		Environment env = getConfiguredEnvironment();
		// NetworkGraph net = new NetworkGraph();
		NetworkGraph net = GraphGenerator.generateWattsStrogatzGraph(10, 4, .2);// WattsStrogatz.generateWattsStrogatzGraph(220,
																				// 4, .2);// new NetworkStructure();
		// net.addAllVertices(219);
		// net.addEdge(0, 2);
		// net.addEdge(0, 2);
		// net.addEdge(1, 0);
		// net.addEdge(2, 3);
		// net.addEdge(2, 1);
		// net.addEdge(3, 1);
		// net.addEdge(3, 0);
		ConsensusParameters params = new ConsensusParameters(.4, .1, .5, 1.0, null);
		for (int i = 0; i < 10; i++)
		{
			AgentState agent = new AgentState(Math.random(), Math.random(), Math.random());
			AgentSystem system = new AgentSystem(agent, net, params);
			env.add(system);
		}

		// env.add(statesChart());
		env.start(38.0, 100000);
		statesAndTimerChart().createChart(env);
		// statesAndTimerChart().plot(env);
		statesAndTimerChart().createChart(env);
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
		statesAndTimerChart().createChart(env);
	}

	public static ChartConfiguration statesAndTimerChart()
	{
		// Create a new plot configuration with specified width (600.0) and height (600.0)
		ChartConfiguration plot = new ChartConfiguration(1000.0, 800.0);
		// plot.
		// Set layout to generate two horizontal plots with plot 0 on top and plot 1 on the bottom
		plot.setLayout(new Integer[][]
		{
				{ 1, 1, 1 },
				{ 1, 1, 1 },
				{ 2, 2, 2 },
				{ 0, 0, 0 } });
		plot.setCombinedDomainPlot(true);
		plot.chartProperties(0).setAxisSelections(null, "systemValue");
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

	public static void main(String args[])
	{
		launch();
	}

}
