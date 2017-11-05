package edu.ucsc.cross.hse.model.consensus.async.consensus.active;

import Jama.Matrix;
import edu.ucsc.cross.hse.models.network.syncconsensus.NetworkParams;
import edu.ucsc.cross.hse.toolbox.math.matrix.MatrixOperator;
import edu.ucsc.hsl.model.synchronization.distributednetwork.WattsStrogatz;

public class ConsensusNodeParams extends NetworkParams
{

	public Double minWrite;
	public Double maxWrite;

	public ConsensusNodeParams(Double flow_gain, Double jump_gain, Double minimum_comm, Double maximum_comm,
	Double min_write, Double max_write, Matrix graph, Matrix laplacian)
	{
		super(flow_gain, jump_gain, minimum_comm, maximum_comm, graph, laplacian);
		minWrite = min_write;
		maxWrite = max_write;
		// TODO Auto-generated constructor stub
	}

	public static ConsensusNodeParams getWattsStrogatzParams(Double flow_gain, Double jump_gain, Double minimum_comm,
	Double maximum_comm, Double min_write, Double max_write, Integer num_nodes)
	{
		Matrix graph = WattsStrogatz.getMatrix(num_nodes, 4, 0.2);
		Matrix laplacian = MatrixOperator.computeLaplacian(graph);
		ConsensusNodeParams params = new ConsensusNodeParams(flow_gain, jump_gain, minimum_comm, maximum_comm,
		min_write, max_write, graph, laplacian);
		return params;
	}
}
