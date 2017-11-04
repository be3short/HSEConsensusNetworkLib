package edu.ucsc.cross.hse.consensus.sync;

import edu.ucsc.cross.hse.core.object.HybridSystem;
import edu.ucsc.cross.hse.models.network.syncconsensus.NetworkParams;

public class AgentSystem extends HybridSystem<NetworkAgentState>
{

	private Integer eventt;
	NetworkParams params;
	SyncNetwork network;

	public AgentSystem(NetworkAgentState state, NetworkParams params, SyncNetwork network)
	{
		super(state);
		eventt = 0;
		this.network = network;
		this.params = params;
	}

	@Override
	public boolean C(NetworkAgentState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean D(NetworkAgentState x)
	{
		boolean jump = false;
		try
		{
			boolean timerExp = network.eventt > eventt;
			jump = timerExp;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return jump;
	}

	@Override
	public void F(NetworkAgentState x, NetworkAgentState x_dot)
	{

		x_dot.stateValue = x.controllerValue;
		x_dot.controllerValue = 0.0;

	}

	@Override
	public void G(NetworkAgentState x, NetworkAgentState x_plus)
	{
		eventt++;
		x_plus.controllerValue = -params.jumpGain * network.getLapValue(this);
		x_plus.stateValue = x.stateValue;
	}

}
