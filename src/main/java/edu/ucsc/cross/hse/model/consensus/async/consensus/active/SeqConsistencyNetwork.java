package edu.ucsc.cross.hse.model.consensus.async.consensus.active;

import Jama.Matrix;
import com.be3short.data.cloning.ObjectCloner;
import edu.ucsc.cross.hse.core.object.HybridSystem;
import edu.ucsc.cross.hse.model.consensus.parameters.ConsensusNodeParams;
import java.util.ArrayList;
import java.util.HashMap;

public class SeqConsistencyNetwork extends HybridSystem<SeqConsistencyNetworkState>
{

	ConsensusNodeParams params;
	public HashMap<SeqConsistencyAgentState, Double> stateValues;
	public Double update;
	public HashMap<SeqConsistencyAgentState, HashMap<SeqConsistencyAgentState, Double>> currents = new HashMap<SeqConsistencyAgentState, HashMap<SeqConsistencyAgentState, Double>>();
	public HashMap<SeqConsistencyAgentState, Double> times = new HashMap<SeqConsistencyAgentState, Double>();
	public ArrayList<SeqConsistencyAgentState> writeQueue = new ArrayList<SeqConsistencyAgentState>();

	public SeqConsistencyNetwork(SeqConsistencyNetworkState state, ConsensusNodeParams params)
	{
		super(state);
		this.params = params;
		update = 0.0;
		stateValues = new HashMap<SeqConsistencyAgentState, Double>();
		initialize();
	}

	private void initialize()
	{
		for (SeqConsistencyAgentState agent : getState().agents)
		{
			stateValues.put(agent, agent.stateValue);
		}
	}

	@Override
	public boolean C(SeqConsistencyNetworkState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean D(SeqConsistencyNetworkState x)
	{
		for (SeqConsistencyAgentState agent : x.agents)
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
	public void F(SeqConsistencyNetworkState x, SeqConsistencyNetworkState x_dot)
	{
		x_dot.elapsed = 1.0;
		for (int i = 0; i < x.agents.size(); i++)
		{
			SeqConsistencyAgentState node = x.agents.get(i);
			SeqConsistencyAgentState node_dot = x_dot.agents.get(i);

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
	public void G(SeqConsistencyNetworkState x, SeqConsistencyNetworkState x_plus)
	{

		double[][] stateValues = new double[getState().agents.size()][1];
		Integer i = 0;
		for (SeqConsistencyAgentState node : x.agents)
		{
			stateValues[i][0] = this.stateValues.get(node);
			i++;
		}
		Matrix stateVals = new Matrix(stateValues);
		Matrix lapCopy = (Matrix) ObjectCloner.xmlClone(params.laplacian);
		Matrix newControlVals = lapCopy.times(stateVals);
		for (int k = 0; k < x.agents.size(); k++)
		{

			SeqConsistencyAgentState node = x.agents.get(k);
			SeqConsistencyAgentState node_plus = x_plus.agents.get(k);
			if (node.communicationTimer <= 0.0 && node.communicationTimer > -1.0)
			{
				Double nextEvent = Math.random() * (params.maxWrite - params.minWrite) + params.minWrite;
				// node_plus.communicationTimer = nextEvent;
				node_plus.controllerValue = (-params.jumpGain * newControlVals.get(k, 0));
				node_plus.stateValue = node.stateValue;
				node_plus.writeValue = node.stateValue;
				node_plus.writeTimer = nextEvent;
				node_plus.communicationTimer = -1.0;
				currents.put(node, updateOtherValues());
				times.put(node, x.elapsed);
				writeQueue.add(node);
			} else if (node.writeTimer <= 0.0 && node.writeTimer > -1.0)
			{
				// if (!writeQueue.isEmpty())
				{
					// if (writeQueue.get(0).equals(node))
					{

						Double nextEvent = Math.random()
						* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
						+ params.minimumCommunicationInterval;
						node_plus.communicationTimer = nextEvent;
						node_plus.controllerValue = node.controllerValue;// (-params.jumpGain * newControlVals.get(k,
																			// 0));
						node_plus.stateValue = node.stateValue;
						// this.stateValues.put(node, node.stateValue);
						// if (times.get(node) > update)
						{
							this.stateValues = currents.get(node);
							// update = times.get(node);
						}
						// node_plus.writeValue = node.stateValue;
						node_plus.writeTimer = -1.0;
						// writeQueue.remove(0);
					}
				}
			}
		}
	}

	public HashMap<SeqConsistencyAgentState, Double> updateOtherValues()
	{
		HashMap<SeqConsistencyAgentState, Double> currs = new HashMap<SeqConsistencyAgentState, Double>();
		for (SeqConsistencyAgentState node : getState().agents)
		{
			currs.put(node, node.stateValue);
		}
		return currs;
	}

	public static SeqConsistencyNetwork generateRandomNetwork(Integer num_nodes, Double state_range,
	Double controller_range, Double comm_min, Double comm_max, Double write_min, Double write_max)
	{
		ArrayList<SeqConsistencyAgentState> nodez = new ArrayList<SeqConsistencyAgentState>();
		for (int i = 0; i < num_nodes; i++)
		{
			SeqConsistencyAgentState node = new SeqConsistencyAgentState(
			((Math.random() - Math.random()) * state_range), ((Math.random() - Math.random()) * controller_range), .2);
			node.info().setName("N" + i);
			nodez.add(node);
		}
		ConsensusNodeParams paramz = ConsensusNodeParams.getWattsStrogatzParams(-.4, .3, comm_min, comm_max, write_min,
		write_max, nodez.size());
		SeqConsistencyNetworkState state = new SeqConsistencyNetworkState(nodez);
		SeqConsistencyNetwork network = new SeqConsistencyNetwork(state, paramz);
		return network;

	}
}
