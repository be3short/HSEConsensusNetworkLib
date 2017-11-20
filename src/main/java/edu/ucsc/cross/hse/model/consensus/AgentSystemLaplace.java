package edu.ucsc.cross.hse.model.consensus;

import Jama.Matrix;
import edu.ucsc.cross.hse.core.container.EnvironmentContent;
import edu.ucsc.cross.hse.core.graph.NetworkEdge;
import edu.ucsc.cross.hse.core.graph.NetworkGraph;
import edu.ucsc.cross.hse.core.object.HybridSystem;

public class AgentSystemLaplace extends HybridSystem<AgentState>
{

	ConsensusParameters params;
	NetworkGraph network;

	public AgentSystemLaplace(AgentState state, NetworkGraph network, ConsensusParameters params)
	{
		super(state);
		this.params = params;
		this.network = network;
	}

	@Override
	public boolean C(AgentState x)
	{
		if (x.communicationTimer >= 0.0)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean D(AgentState x)
	{

		if (x.communicationTimer <= 0.0)
		{
			return true;
		}

		return false;
	}

	@Override
	public void F(AgentState x, AgentState x_dot)
	{
		x_dot.systemValue = x.controllerValue;
		x_dot.controllerValue = 0.0;
		x_dot.communicationTimer = -1.0;
	}

	public Matrix getLaplacian()
	{
		return network.getLaplacian();
	}

	// @Override
	public void G2(AgentState x, AgentState x_plus)
	{
		double stateValArray[][] = new double[network.vertexSet().size()][1];
		for (int v = 0; v < network.vertexSet().size(); v++)
		{

			stateValArray[v][0] = ((AgentState) EnvironmentContent.getSystem(this, v).getState()).systemValue;

		}
		Matrix newControlVals = getLaplacian().times(new Matrix(stateValArray));

		if (x.communicationTimer <= 0.0)
		{
			Double nextEvent = Math.random()
			* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
			+ params.minimumCommunicationInterval;
			x_plus.communicationTimer = nextEvent;
			x_plus.controllerValue = (-params.jumpGain * newControlVals.get(x.data().getAddress(), 0));
			x_plus.systemValue = x.systemValue;
		}

	}

	public Matrix N2(AgentState x)
	{
		double stateValArray[][] = new double[network.vertexSet().size()][1];
		for (int v = 0; v < network.vertexSet().size(); v++)
		{

			stateValArray[v][0] = ((AgentState) EnvironmentContent.getSystem(this, v).getState()).systemValue;

		}
		return (new Matrix(stateValArray));
	}

	@Override
	public void G(AgentState x, AgentState x_plus)
	{
		double val = 0.0;
		for (NetworkEdge v : network.edgesOf(x.data().getAddress()))
		{

			val += -params.jumpGain * (x.systemValue
			- ((AgentState) EnvironmentContent.getSystem(this, v.getDestination()).getState()).systemValue);

		}

		if (x.communicationTimer <= 0.0)
		{
			Double nextEvent = Math.random()
			* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
			+ params.minimumCommunicationInterval;
			x_plus.communicationTimer = nextEvent;
			x_plus.controllerValue = val;
			x_plus.systemValue = x.systemValue;
		}

	}

	public Matrix N(AgentState x)
	{
		double stateValArray[][] = new double[network.vertexSet().size()][1];
		for (int v = 0; v < network.vertexSet().size(); v++)
		{

			stateValArray[v][0] = ((AgentState) EnvironmentContent.getSystem(this, v).getState()).systemValue;

		}
		return (new Matrix(stateValArray));
	}
}
