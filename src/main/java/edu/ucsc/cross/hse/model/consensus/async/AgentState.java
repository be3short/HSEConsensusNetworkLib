package edu.ucsc.cross.hse.model.consensus.async;

import edu.ucsc.cross.hse.core.object.Objects;

public class AgentState extends Objects
{

	public Double stateValue;
	public Double controllerValue;
	public Double communicationTimer;

	public AgentState()
	{

		stateValue = 0.0;
		controllerValue = 0.0;
		communicationTimer = 0.0;

	}

	public AgentState(Double state_val, Double control_val, Double comm_timer)
	{
		stateValue = state_val;
		controllerValue = control_val;
		communicationTimer = comm_timer;

	}
}
