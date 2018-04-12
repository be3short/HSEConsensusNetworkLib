package edu.ucsc.cross.hse.model.consensus;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * The consensus network agent state.
 * 
 * @author Brendan Short
 *
 */
public class AgentState extends DataStructure
{

	/**
	 * System value that is the target of the consensus
	 */
	public double systemValue;

	/**
	 * Controller value that drives the system value
	 */
	public double controllerValue;

	/**
	 * Communication timer that triggers the communication events
	 */
	public double communicationTimer;

	public AgentState(double system_value, double controller_value, double timer_value)
	{
		this.systemValue = system_value;
		this.controllerValue = controller_value;
		this.communicationTimer = timer_value;
	}
}
