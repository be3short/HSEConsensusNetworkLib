package edu.ucsc.cross.hse.model.consensus.synchronous;

import Jama.Matrix;
import com.be3short.data.cloning.ObjectCloner;
import edu.ucsc.cross.hse.core.object.HybridSystem;
import edu.ucsc.cross.hse.model.consensus.parameters.NetworkParameters;
import java.util.ArrayList;

public class SyncNetworkSystem extends HybridSystem<SyncNetworkState>
{

	NetworkParameters params;

	public SyncNetworkSystem(SyncNetworkState state, NetworkParameters params)
	{
		super(state);
		this.params = params;
	}

	@Override
	public boolean C(SyncNetworkState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean D(SyncNetworkState x)
	{
		// TODO Auto-generated method stub
		return x.eventTimer <= 0.0;
	}

	@Override
	public void F(SyncNetworkState x, SyncNetworkState x_dot)
	{
		x_dot.eventTimer = -1.0;
		for (int i = 0; i < x.nodes.size(); i++)
		{
			SyncAgentState node = x.nodes.get(i);
			SyncAgentState node_dot = x_dot.nodes.get(i);

			node_dot.stateValue = node.controllerValue;
			node_dot.controllerValue = 0.0;

		}
	}

	@SuppressWarnings("unused")
	@Override
	public void G(SyncNetworkState x, SyncNetworkState x_plus)
	{
		Double nextEvent = Math.random() * (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
		+ params.minimumCommunicationInterval;
		x_plus.eventTimer = nextEvent;
		double[][] stateValues = new double[getState().nodes.size()][1];
		Integer i = 0;
		for (SyncAgentState node : x.nodes)
		{
			stateValues[i][0] = x.nodes.get(i).stateValue;
			i++;
		}
		Matrix stateVals = new Matrix(stateValues);
		Matrix lapCopy = (Matrix) ObjectCloner.xmlClone(params.laplacian);
		Matrix newControlVals = lapCopy.times(stateVals);
		i = 0;
		for (SyncAgentState node : x_plus.nodes)
		{

			node.controllerValue = (-params.jumpGain * newControlVals.get(i++, 0));

		}
	}

	public static SyncNetworkSystem generateRandomNetwork(Integer num_nodes, Double state_range,
	Double controller_range)
	{
		ArrayList<SyncAgentState> nodez = new ArrayList<SyncAgentState>();
		for (int i = 0; i < num_nodes; i++)
		{
			SyncAgentState node = new SyncAgentState(((Math.random() - Math.random()) * state_range),
			((Math.random() - Math.random()) * controller_range));
			node.info().setName("N" + i);
			nodez.add(node);
		}
		NetworkParameters paramz = NetworkParameters.getWattsStrogatzParams(-.4, .3, 0.7, 1.5, nodez.size());
		SyncNetworkState state = new SyncNetworkState(nodez, .5);
		SyncNetworkSystem network = new SyncNetworkSystem(state, paramz);
		return network;

	}

	public static void main(String args[])
	{

	}
}
