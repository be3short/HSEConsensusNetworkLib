package edu.ucsc.cross.hse.model.consensus.async.consensus.active;

import edu.ucsc.cross.hse.core.object.ObjectSet;

public class SeqConsistencyAgentState extends ObjectSet
{

	public Double stateValue;
	public Double controllerValue;
	public Double communicationTimer;
	public Double writeValue;
	public Double writeTimer;

	public SeqConsistencyAgentState()
	{

		stateValue = 0.0;
		controllerValue = 0.0;
		communicationTimer = 0.0;

	}

	public SeqConsistencyAgentState(Double state_val, Double control_val, Double comm_timer)
	{
		stateValue = state_val;
		controllerValue = control_val;
		communicationTimer = comm_timer;
		writeValue = 0.0;
		writeTimer = -1.0;

	}
}
