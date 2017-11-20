package edu.ucsc.cross.hse.model.consensus;

import Jama.Matrix;

public class ConsensusParameters
{

	public Double flowGain;
	public Double jumpGain;
	public Double minimumCommunicationInterval;
	public Double maximumCommunicationInterval;
	public Matrix laplacian;

	public ConsensusParameters(Double flow_gain, Double jump_gain, Double minimum_comm, Double maximum_comm,
	Matrix laplacian)
	{
		this.flowGain = flow_gain;
		this.jumpGain = jump_gain;
		this.minimumCommunicationInterval = minimum_comm;
		this.maximumCommunicationInterval = maximum_comm;
		this.laplacian = laplacian;
	}

}
