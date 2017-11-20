package edu.ucsc.cross.hse.model.consensus;

import Jama.Matrix;
import edu.ucsc.cross.hse.core.container.EnvironmentContent;
import edu.ucsc.cross.hse.core.object.HybridSystem;
import edu.ucsc.cross.hse.model.consensus.graph.NetworkGraph;

public class AgentSystem extends HybridSystem<AgentState>
{

	ConsensusParameters params;
	NetworkGraph network;

	public AgentSystem(AgentState state, NetworkGraph network, ConsensusParameters params)
	{
		super(state);
		this.params = params;
		this.network = network;
	}

	@Override
	public boolean C(AgentState x)
	{
		return true;
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
		if (params.laplacian == null)
		{
			params.laplacian = NetworkGraph.computeLaplacian(network.getAdjacencyMatrix());
		}
		return params.laplacian;
		// return NetworkStructure.computeLaplacian(network.getAdjacencyMatrix());//
		// )computeLaplacian(network.getAdjacencyMatrix());
	}

	// public static Matrix computeLaplacian(Matrix matrix)
	// {
	// Matrix laplacian = network.
	// return laplacian;
	// }

	@SuppressWarnings("unused")
	@Override
	public void G(AgentState x, AgentState x_plus)
	{
		// Matrix adjacency = new Matrix(new double[][]
		// { network.getAdjacencyMatrix().getArray()[x.extension().getAddress()] });
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
			x_plus.controllerValue = (-params.jumpGain * newControlVals.get(x.extension().getAddress(), 0));
			x_plus.systemValue = x.systemValue;
		}

	}

	// public static AgentState generateRandomNetwork(Integer num_nodes, Double state_range, Double controller_range,
	// Double min_comm_time, Double max_comm_time)
	// {
	// ArrayList<AsyncAgentState> nodez = new ArrayList<AsyncAgentState>();
	// for (int i = 0; i < num_nodes; i++)
	// {
	// AsyncAgentState node = new AsyncAgentState(((Math.random() - Math.random()) * state_range),
	// ((Math.random() - Math.random()) * controller_range), .2);
	// node.extension().setName("N" + i);
	// nodez.add(node);
	// }
	// ConsensusParameters paramz = ConsensusParameters.getWattsStrogatzParams(-.4, .3, min_comm_time, max_comm_time,
	// nodez.size());
	// AgentState state = new AgentState(nodez);
	// AsyncNetworkSystem network = new AsyncNetworkSystem(state, paramz);
	// return network;
	//
	// }

}
