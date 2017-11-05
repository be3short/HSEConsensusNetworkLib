package edu.ucsc.cross.hse.model.consensus.synchronous;

import edu.ucsc.cross.hse.core.object.Objects;
import java.util.ArrayList;

public class SyncNetworkState extends Objects
{

	public ArrayList<SyncAgentState> nodes;
	public double eventTimer;

	public SyncNetworkState(ArrayList<SyncAgentState> nodez, double event_timer)
	{
		nodes = nodez;
		eventTimer = event_timer;
	}

	public Integer totalNodes()
	{
		return nodes.size();
	}
}
