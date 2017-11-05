package edu.ucsc.cross.hse.model.consensus.async.consensus;

import Jama.Matrix;
import com.be3short.data.cloning.ObjectCloner;
import edu.ucsc.cross.hse.core.object.HybridSystem;
import java.util.ArrayList;
import java.util.HashMap;

public class AsyncConsensusNetwork extends HybridSystem<NetworkState>
{

	ConsensusNodeParams params;
	public HashMap<AgentState, Double> stateValues;

	public AsyncConsensusNetwork(NetworkState state, ConsensusNodeParams params)
	{
		super(state);
		this.params = params;
		stateValues = new HashMap<AgentState, Double>();
		initialize();
	}

	private void initialize()
	{
		for (AgentState agent : getState().agents)
		{
			stateValues.put(agent, agent.stateValue);
		}
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
			if (agent.communicationTimer <= 0.0 && agent.communicationTimer > -1.0
			|| agent.writeTimer <= 0.0 && agent.writeTimer > -1.0)
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
			if (node.writeTimer < 0.0)
			{
				node_dot.communicationTimer = -1.0;
			} else
			{
				node_dot.communicationTimer = 0.0;
			}
			if (node.writeTimer >= 0.0)
			{
				node_dot.writeTimer = -1.0;
			} else
			{
				node_dot.writeTimer = 0.0;
			}
			node_dot.writeValue = 0.0;
		}
	}

	@Override
	public void G(NetworkState x, NetworkState x_plus)
	{

		double[][] stateValues = new double[getState().agents.size()][1];
		Integer i = 0;
		for (AgentState node : x.agents)
		{
			stateValues[i][0] = this.stateValues.get(node);
			i++;
		}
		Matrix stateVals = new Matrix(stateValues);
		Matrix lapCopy = (Matrix) ObjectCloner.xmlClone(params.laplacian);
		Matrix newControlVals = lapCopy.times(stateVals);
		for (int k = 0; k < x.agents.size(); k++)
		{

			AgentState node = x.agents.get(k);
			AgentState node_plus = x_plus.agents.get(k);
			if (node.communicationTimer <= 0.0 && node.communicationTimer > -1.0)
			{
				Double nextEvent = Math.random() * (params.maxWrite - params.minWrite) + params.minWrite;
				// node_plus.communicationTimer = nextEvent;
				node_plus.controllerValue = (-params.jumpGain * newControlVals.get(k, 0));
				node_plus.stateValue = node.stateValue;
				node_plus.writeValue = node.stateValue;
				node_plus.writeTimer = nextEvent;
				node_plus.communicationTimer = -1.0;
			} else if (node.writeTimer <= 0.0 && node.writeTimer > -1.0)
			{
				Double nextEvent = Math.random()
				* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
				+ params.minimumCommunicationInterval;
				node_plus.communicationTimer = nextEvent;
				node_plus.controllerValue = node.controllerValue;// (-params.jumpGain * newControlVals.get(k, 0));
				node_plus.stateValue = node.stateValue;
				this.stateValues.put(node, node.stateValue);
				// node_plus.writeValue = node.stateValue;
				node_plus.writeTimer = -1.0;

			}
		}
	}

	public static AsyncConsensusNetwork generateRandomNetwork(Integer num_nodes, Double state_range,
	Double controller_range, Double comm_min, Double comm_max, Double write_min, Double write_max)
	{
		ArrayList<AgentState> nodez = new ArrayList<AgentState>();
		for (int i = 0; i < num_nodes; i++)
		{
			AgentState node = new AgentState(((Math.random() - Math.random()) * state_range),
			((Math.random() - Math.random()) * controller_range), .2);
			node.info().setName("N" + i);
			nodez.add(node);
		}
		ConsensusNodeParams paramz = ConsensusNodeParams.getWattsStrogatzParams(-.4, .3, comm_min, comm_max, write_max,
		write_min, nodez.size());
		NetworkState state = new NetworkState(nodez);
		AsyncConsensusNetwork network = new AsyncConsensusNetwork(state, paramz);
		return network;

	}
}
