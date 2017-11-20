package edu.ucsc.cross.hse.model.consensus;

import edu.ucsc.cross.hse.core.object.ObjectSet;

public class AgentState extends ObjectSet
{

	public double systemValue;
	public double controllerValue;
	public double communicationTimer;

	public AgentState(double system_value, double controller_value, double timer_value)
	{
		this.data().setName("Agent");
		this.systemValue = system_value;
		this.controllerValue = controller_value;
		this.communicationTimer = timer_value;
	}
}
