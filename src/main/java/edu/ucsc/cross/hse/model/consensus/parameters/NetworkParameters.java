package edu.ucsc.cross.hse.model.consensus.parameters;

import Jama.Matrix;
import edu.ucsc.cross.hse.model.consensus.graph.WattsStrogatz;
import edu.ucsc.cross.hse.model.consensus.util.MatrixOperator;

public class NetworkParameters
{

	public Double flowGain;
	public Double jumpGain;
	public Double minimumCommunicationInterval;
	public Double maximumCommunicationInterval;
	public Matrix graph;
	public Matrix laplacian;

	public NetworkParameters(Double flow_gain, Double jump_gain, Double minimum_comm, Double maximum_comm, Matrix graph,
	Matrix laplacian)
	{
		this.flowGain = flow_gain;
		this.jumpGain = jump_gain;
		this.minimumCommunicationInterval = minimum_comm;
		this.maximumCommunicationInterval = maximum_comm;
		this.graph = graph;
		this.laplacian = laplacian;
	}

	public static NetworkParameters getWattsStrogatzParams(Double flow_gain, Double jump_gain, Double minimum_comm,
	Double maximum_comm, Integer num_nodes)
	{
		Matrix graph = WattsStrogatz.getMatrix(num_nodes, 4, 0.2);
		Matrix laplacian = MatrixOperator.computeLaplacian(graph);
		NetworkParameters params = new NetworkParameters(flow_gain, jump_gain, minimum_comm, maximum_comm, graph, laplacian);
		return params;
	}

}
