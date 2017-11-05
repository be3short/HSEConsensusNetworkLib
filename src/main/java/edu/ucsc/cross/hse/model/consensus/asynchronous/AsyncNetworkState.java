package edu.ucsc.cross.hse.model.consensus.asynchronous;

import edu.ucsc.cross.hse.core.object.Objects;
import java.util.ArrayList;

public class AsyncNetworkState extends Objects
{

	public ArrayList<AsyncAgentState> agents;

	public AsyncNetworkState(ArrayList<AsyncAgentState> agents)
	{
		this.agents = agents;
	}
}
