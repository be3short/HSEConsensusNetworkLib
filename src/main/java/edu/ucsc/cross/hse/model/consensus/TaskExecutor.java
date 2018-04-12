package edu.ucsc.cross.hse.model.consensus;

/*
 * This is an example of what an empty task execution class looks like. You can copy this code to create your own
 * version.
 */
public class TaskExecutor
{
	//
	//	// Everything within the taskQueue() method will be executed
	//	@Override
	//	public void taskQueue()
	//	{
	//		// Your tasks go here
	//		// runSyncNetwork();
	//		// runASyncAgentNetwork();
	//		// runASyncConsensusNetwork();
	//		runASyncAgentNetwork();
	//		// runASyncSeqConsensusNetwork();
	//		// openEnvironmentAndPlot();
	//		// SampleAndHoldTasks.signalGeneratorSampleAndHoldSimulation(20.0, .3, .1);
	//	}
	//
	//	public static Environment getConfiguredEnvironment()
	//	{
	//		Environment env = new Environment();
	//		env.getSettings().getLogSettings().printInfo = true;
	//		env.getSettings().getLogSettings().printDebug = true;
	//		env.getSettings().getLogSettings().printError = true;
	//		env.getSettings().getLogSettings().printIntegratorExceptions = true;
	//		env.getSettings().getOutputSettings().dataPointInterval = .3;
	//		env.getSettings().getOutputSettings().appendFilesWithNumericDate = false;
	//		env.getSettings().getOutputSettings().saveChartsToFile = false;
	//		env.getSettings().getOutputSettings().saveEnvironmentToFile = false;
	//		env.getSettings().getOutputSettings().saveConfigurationToFile = false;
	//		// env.getSettings().getOutputSettings().chartFileFormat = ImageFormat.EPS;
	//		return env;
	//	}
	//
	//	public void runASyncAgentNetwork()
	//	{
	//
	//		Environment env = getConfiguredEnvironment();
	//		// NetworkGraph net = new NetworkGraph();
	//		NetworkGraph net = NetworkGraphFactory.generateWattsStrogatzGraph(500, 4, .4);// WattsStrogatz.generateWattsStrogatzGraph(220,
	//		// 4, .2);// new NetworkStructure();
	//		// net.addAllVertices(219);
	//		// net.addEdge(0, 2);
	//		// net.addEdge(0, 2);
	//		// net.addEdge(1, 0);
	//		// net.addEdge(2, 3);
	//		// net.addEdge(2, 1);
	//		// net.addEdge(3, 1);
	//		// net.addEdge(3, 0);
	//		ConsensusParameters params = new ConsensusParameters(.4, .3, .5, 1.0, null);
	//		for (int i = 0; i < 500; i++)
	//		{
	//			AgentState agent = new AgentState(Math.random(), Math.random(), Math.random());
	//			AgentSystem system = new AgentSystem(agent, net, params);
	//			env.add(system);
	//		}
	//		env.settings.getOutputSettings().saveConfigurationToFile = false;
	//		env.settings.getOutputSettings().saveEnvironmentToFile = false;
	//		// env.add(statesChart());
	//		env.start(8.0, 100000);
	//		statesAndTimerChart().createChart(env);
	//		// statesAndTimerChart().plot(env);
	//		statesAndTimerChart().createChart(env);
	//	}
	//
	//	public void openEnvironmentAndPlot()
	//	{
	//		File envFile = new FileChooser().showOpenDialog(new Stage());
	//		Environment env = Environment.createEnvironment(envFile);
	//		File datFile = new FileChooser().showOpenDialog(new Stage());
	//		// EnvironmentData dat = DataMonitor.getCSVData(datFile);
	//		// env.getData().load(dat.getStoreTimes(), dat.getGlobalStateData());
	//		// env.getOutputs().generateOutputs(env, true);
	//		// env.add(xyCombination());
	//
	//		// statesAndTimerChart().plot(env);
	//		statesAndTimerChart().createChart(env);
	//	}
	//
	//	public static ChartConfiguration statesAndTimerChart()
	//	{
	//		// Create a new plot configuration with specified width (600.0) and height (600.0)
	//		ChartConfiguration plot = new ChartConfiguration(600.0, 600.0);
	//		plot.getChartTemplate().removeLegend();// .getLegend().setVisible(false);.getChartTemplate().getLegend().setVisible(false);//
	//		plot.setLayout(new Integer[][]
	//		{
	//				{ 1, 1, 1 },
	//				{ 2, 2, 2 },
	//				{ 0, 0, 0 } });
	//		plot.setCombinedDomainPlot(true);
	//
	//		plot.getCombinedPlot().getDomainAxis().setLabel("Time (sec)");
	//		plot.chartProperties(0).setAxisSelections(null, "systemValue");
	//		plot.chartProperties(1).setAxisSelections(null, "controllerValue");
	//		plot.chartProperties(2).setAxisSelections(null, "communicationTimer");
	//
	//		plot.chartProperties(0).setAxisLabels("Time (sec)", "System Value");
	//		plot.chartProperties(1).setAxisLabels("Time (sec)", "Controller Value");
	//		plot.chartProperties(2).setAxisLabels(null, "Communication Timer");
	//		// plot.getCombinedPlot().getParent().getLegendItems()..getChartTemplate().getLegend().setVisible(false);//
	//		plot.setDisplayGlobalLegend(false);
	//		plot.addMainTitle("Asynchronous Consensus Network", new Font("Tahoma", Font.BOLD, 14));
	//
	//		return plot;
	//	}
	//
	//	public static void main(String args[])
	//	{
	//		launch();
	//	}

}
