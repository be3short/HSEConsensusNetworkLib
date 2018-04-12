package edu.ucsc.cross.hse.model.consensus;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import network.BasicNetwork;

public class AgentSystem extends HybridSystem<AgentState>
{

	/**
	 * Consensus network parameters
	 */
	ConsensusParameters params;

	/**
	 * Agent connection network
	 */
	BasicNetwork<AgentState> network;

	/**
	 * Constructor for the agent system
	 * 
	 * @param state
	 *            agent state
	 * @param network
	 *            agent network (shared)
	 * @param params
	 *            consensus network parameters
	 */
	public AgentSystem(AgentState state, BasicNetwork<AgentState> network, ConsensusParameters params)
	{
		super(state);
		this.params = params;
		this.network = network;
	}

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(AgentState x)
	{
		if (x.communicationTimer >= 0.0)
		{
			return true;
		}
		return false;
	}

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            state derivative
	 */
	@Override
	public boolean D(AgentState x)
	{
		if (getSynchronizationAgent().communicationTimer <= 0.0)
		{
			return true;
		}
		return false;
	}

	/**
	 * Jump set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public void F(AgentState x, AgentState x_dot)
	{
		x_dot.systemValue = x.controllerValue;
		x_dot.controllerValue = 0.0;
		if (this.getComponents().getState().equals(getSynchronizationAgent()))
		{
			x_dot.communicationTimer = -1.0;
		}
	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            updated state
	 */
	@Override
	public void G(AgentState x, AgentState x_plus)
	{
		double controllerUpdate = 0.0;
		for (AgentState connectedAgent : network.getConnections(getComponents().getState()))
		{
			controllerUpdate += -params.jumpGain * (x.systemValue - connectedAgent.systemValue);
		}
		if (x.communicationTimer <= 0.0)
		{
			Double nextEvent = Math.random()
			* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
			+ params.minimumCommunicationInterval;
			x_plus.communicationTimer = nextEvent;
		}

		x_plus.controllerValue = controllerUpdate;
		x_plus.systemValue = x.systemValue;
	}

	/**
	 * Get the agent whose timer triggers the communication event (one agent in
	 * network if the system is synchronous, or every agent in network if the
	 * system is asynchronous)
	 */
	public AgentState getSynchronizationAgent()
	{

		AgentState syncAgent = getComponents().getState();
		if (params.synchronous)
		{
			syncAgent = this.network.getAllVertices().get(0);
		}
		return syncAgent;
	}
}
