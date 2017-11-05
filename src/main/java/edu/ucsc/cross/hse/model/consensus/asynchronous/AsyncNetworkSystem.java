package edu.ucsc.cross.hse.model.consensus.asynchronous;

import Jama.Matrix;
import com.be3short.data.cloning.ObjectCloner;
import edu.ucsc.cross.hse.core.object.HybridSystem;
import edu.ucsc.cross.hse.model.consensus.parameters.NetworkParameters;
import java.util.ArrayList;

public class AsyncNetworkSystem extends HybridSystem<AsyncNetworkState>
{

	NetworkParameters params;

	public AsyncNetworkSystem(AsyncNetworkState state, NetworkParameters params)
	{
		super(state);
		this.params = params;
	}

	@Override
	public boolean C(AsyncNetworkState x)
	{
		return true;
	}

	@Override
	public boolean D(AsyncNetworkState x)
	{
		for (AsyncAgentState agent : x.agents)
		{
			if (agent.communicationTimer <= 0.0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void F(AsyncNetworkState x, AsyncNetworkState x_dot)
	{

		for (int i = 0; i < x.agents.size(); i++)
		{
			AsyncAgentState node = x.agents.get(i);
			AsyncAgentState node_dot = x_dot.agents.get(i);

			node_dot.stateValue = node.controllerValue;
			node_dot.controllerValue = 0.0;
			node_dot.communicationTimer = -1.0;

		}
	}

	@SuppressWarnings("unused")
	@Override
	public void G(AsyncNetworkState x, AsyncNetworkState x_plus)
	{

		double[][] stateValues = new double[getState().agents.size()][1];
		Integer i = 0;
		for (AsyncAgentState node : x.agents)
		{
			stateValues[i][0] = x.agents.get(i).stateValue;
			i++;
		}
		Matrix stateVals = new Matrix(stateValues);
		Matrix lapCopy = (Matrix) ObjectCloner.xmlClone(params.laplacian);
		Matrix newControlVals = lapCopy.times(stateVals);
		for (int k = 0; k < x.agents.size(); k++)
		{
			AsyncAgentState node = x.agents.get(k);
			AsyncAgentState node_plus = x_plus.agents.get(k);
			if (node.communicationTimer <= 0.0)
			{
				Double nextEvent = Math.random()
				* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
				+ params.minimumCommunicationInterval;
				node_plus.communicationTimer = nextEvent;
				node_plus.controllerValue = (-params.jumpGain * newControlVals.get(k, 0));
				node_plus.stateValue = node.stateValue;
			}
		}
	}

	public static AsyncNetworkSystem generateRandomNetwork(Integer num_nodes, Double state_range,
	Double controller_range, Double min_comm_time, Double max_comm_time)
	{
		ArrayList<AsyncAgentState> nodez = new ArrayList<AsyncAgentState>();
		for (int i = 0; i < num_nodes; i++)
		{
			AsyncAgentState node = new AsyncAgentState(((Math.random() - Math.random()) * state_range),
			((Math.random() - Math.random()) * controller_range), .2);
			node.info().setName("N" + i);
			nodez.add(node);
		}
		NetworkParameters paramz = NetworkParameters.getWattsStrogatzParams(-.4, .3, min_comm_time, max_comm_time,
		nodez.size());
		AsyncNetworkState state = new AsyncNetworkState(nodez);
		AsyncNetworkSystem network = new AsyncNetworkSystem(state, paramz);
		return network;

	}
}
