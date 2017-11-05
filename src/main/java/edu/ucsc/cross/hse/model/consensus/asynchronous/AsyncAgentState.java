package edu.ucsc.cross.hse.model.consensus.asynchronous;

import edu.ucsc.cross.hse.core.object.Objects;

public class AsyncAgentState extends Objects
{

	public Double stateValue;
	public Double controllerValue;
	public Double communicationTimer;

	public AsyncAgentState()
	{

		stateValue = 0.0;
		controllerValue = 0.0;
		communicationTimer = 0.0;

	}

	public AsyncAgentState(Double state_val, Double control_val, Double comm_timer)
	{
		stateValue = state_val;
		controllerValue = control_val;
		communicationTimer = comm_timer;

	}
}
