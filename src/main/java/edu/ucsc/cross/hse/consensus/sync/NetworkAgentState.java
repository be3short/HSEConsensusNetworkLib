package edu.ucsc.cross.hse.consensus.sync;

import edu.ucsc.cross.hse.core.object.Objects;

public class NetworkAgentState extends Objects
{

	public double stateValue;
	public double controllerValue;

	public NetworkAgentState()
	{

		stateValue = 0.0;
		controllerValue = 0.0;

	}

	public NetworkAgentState(Double state_val, Double control_val)
	{
		stateValue = state_val;
		controllerValue = control_val;

	}

}
