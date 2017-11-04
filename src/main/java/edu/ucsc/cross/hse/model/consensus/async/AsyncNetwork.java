package edu.ucsc.cross.hse.model.consensus.async;

import Jama.Matrix;
import com.be3short.data.cloning.ObjectCloner;
import edu.ucsc.cross.hse.core.object.HybridSystem;
import edu.ucsc.cross.hse.models.network.syncconsensus.NetworkParams;
import java.util.ArrayList;

public class AsyncNetwork extends HybridSystem<NetworkState>
{

	NetworkParams params;

	public AsyncNetwork(NetworkState state, NetworkParams params)
	{
		super(state);
		this.params = params;
	}

	@Override
	public boolean C(NetworkState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean D(NetworkState x)
	{
		for (AgentState agent : x.agents)
		{
			if (agent.communicationTimer <= 0.0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void F(NetworkState x, NetworkState x_dot)
	{

		for (int i = 0; i < x.agents.size(); i++)
		{
			AgentState node = x.agents.get(i);
			AgentState node_dot = x_dot.agents.get(i);

			node_dot.stateValue = node.controllerValue;
			node_dot.controllerValue = 0.0;
			node_dot.communicationTimer = -1.0;

		}
	}

	@Override
	public void G(NetworkState x, NetworkState x_plus)
	{

		double[][] stateValues = new double[getState().agents.size()][1];
		Integer i = 0;
		for (AgentState node : x.agents)
		{
			stateValues[i][0] = x.agents.get(i).stateValue;
			i++;
		}
		Matrix stateVals = new Matrix(stateValues);
		Matrix lapCopy = (Matrix) ObjectCloner.xmlClone(params.laplacian);
		Matrix newControlVals = lapCopy.times(stateVals);
		for (int k = 0; k < x.agents.size(); k++)
		{

			AgentState node = x.agents.get(k);
			AgentState node_plus = x_plus.agents.get(k);
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

	public static AsyncNetwork generateRandomNetwork(Integer num_nodes, Double state_range, Double controller_range)
	{
		ArrayList<AgentState> nodez = new ArrayList<AgentState>();
		for (int i = 0; i < num_nodes; i++)
		{
			AgentState node = new AgentState(((Math.random() - Math.random()) * state_range),
			((Math.random() - Math.random()) * controller_range), .2);
			node.info().setName("N" + i);
			nodez.add(node);
		}
		NetworkParams paramz = NetworkParams.getWattsStrogatzParams(-.4, .3, 0.7, 1.5, nodez.size());
		NetworkState state = new NetworkState(nodez);
		AsyncNetwork network = new AsyncNetwork(state, paramz);
		return network;

	}
}
