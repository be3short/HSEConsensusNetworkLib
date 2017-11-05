package edu.ucsc.cross.hse.model.consensus.synchronous;

import edu.ucsc.cross.hse.core.object.Objects;

public class SyncAgentState extends Objects
{

	public double stateValue;
	public double controllerValue;

	public SyncAgentState()
	{

		stateValue = 0.0;
		controllerValue = 0.0;

	}

	public SyncAgentState(Double state_val, Double control_val)
	{
		stateValue = state_val;
		controllerValue = control_val;

	}
}