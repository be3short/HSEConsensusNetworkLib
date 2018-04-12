package edu.ucsc.cross.hse.model.consensus;

import java.util.ArrayList;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.modeling.SystemSet;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import network.BasicNetwork;

public class NodeSims
{

	public static void main(String args[])
	{
		runASyncAgentNetwork(3, 8);
	}

	public static void runASyncAgentNetwork(Integer connections, Integer numNodes)
	{

		EnvironmentSettings settings = new EnvironmentSettings();
		ExecutionParameters parameters = new ExecutionParameters(20.0, 1000000, 1.0);
		SystemSet systems = new SystemSet();

		BasicNetwork<AgentState> network = new BasicNetwork<AgentState>();

		ConsensusParameters params = new ConsensusParameters(.4, .3, .5, 1.0, false);
		for (int i = 0; i < numNodes; i++)
		{
			AgentState agent = new AgentState(Math.random(), Math.random(), Math.random() + .05);
			network.topology.addVertex(agent);
			AgentSystem system = new AgentSystem(agent, network, params);
			systems.add(system);

		}

		ArrayList<AgentState> conns = new ArrayList<AgentState>(network.getAllVertices());

		for (AgentState node : network.getAllVertices())
		{
			for (int coni = 0; coni < connections; coni++)
			{
				AgentState connect = conns.get(0);
				while (connect.equals(node))
				{

					connect = conns.get(Math.round(conns.size()) - 1);
				}
				network.establishConnection(node, connect);
				conns.remove(connect);
				if (conns.size() <= 1)
				{
					conns.clear();
					conns.addAll(network.getAllVertices());
				}
			}
		}
		HSEnvironment env = HSEnvironment.create(systems, parameters, settings);
		TrajectorySet solution = env.run();
		generateFullStateFigure(solution).display();

	}

	/**
	 * Generate a figure with all four bouncing ball state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all four bouncing ball state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(800, 600);

		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "systemValue");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "controllerValue");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "communicationTimer");

		figure.addComponent(0, 0, xPos);
		figure.addComponent(1, 0, xVel);
		figure.addComponent(2, 0, yPos);

		ChartUtils.configureLabels(xPos, "Time (sec)", "System Value", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Controller Value", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "Communication Timer", null, true);

		return figure;
	}
}
