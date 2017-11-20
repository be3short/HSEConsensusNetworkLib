package edu.ucsc.cross.hse.model.consensus;

import edu.ucsc.cross.hse.core.graph.NetworkEdge;
import edu.ucsc.cross.hse.core.graph.NetworkGraph;
import edu.ucsc.cross.hse.core.object.HybridSystem;

public class AgentSystem extends HybridSystem<AgentState>
{

	ConsensusParameters params;
	NetworkGraph network;

	public AgentSystem(AgentState state, NetworkGraph network, ConsensusParameters params)
	{
		super(state);
		this.params = params;
		this.network = network;
	}

	@Override
	public boolean C(AgentState x)
	{
		if (x.communicationTimer >= 0.0)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean D(AgentState x)
	{
		if (x.communicationTimer <= 0.0)
		{
			return true;
		}
		return false;
	}

	@Override
	public void F(AgentState x, AgentState x_dot)
	{
		x_dot.systemValue = x.controllerValue;
		x_dot.controllerValue = 0.0;
		x_dot.communicationTimer = -1.0;
	}

	@Override
	public void G(AgentState x, AgentState x_plus)
	{
		double controllerUpdate = 0.0;
		for (NetworkEdge v : network.edgesOf(x.data().getAddress()))
		{
			controllerUpdate += -params.jumpGain
			* (x.systemValue - ((AgentState) environment().getAddressObject(v.getDestination())).systemValue);
		}
		Double nextEvent = Math.random() * (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
		+ params.minimumCommunicationInterval;

		x_plus.communicationTimer = nextEvent;
		x_plus.controllerValue = controllerUpdate;
		x_plus.systemValue = x.systemValue;
	}
}
