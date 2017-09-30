package edu.ucsc.cross.hse.models.network.syncconsensus;

import edu.ucsc.cross.hse2.core.obj.structure.State;

public class NodeState extends State
{

	public double stateValue;
	public double controllerValue;

	public NodeState()
	{
		super("Node State");
		stateValue = 0.0;
		controllerValue = 0.0;

	}

	public NodeState(Double state_val, Double control_val)
	{
		super("Node State");
		stateValue = state_val;
		controllerValue = control_val;

	}
}
