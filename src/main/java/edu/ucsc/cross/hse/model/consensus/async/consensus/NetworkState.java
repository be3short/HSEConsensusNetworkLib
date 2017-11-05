package edu.ucsc.cross.hse.model.consensus.async.consensus;

import edu.ucsc.cross.hse.core.object.Objects;
import java.util.ArrayList;

public class NetworkState extends Objects
{

	public ArrayList<AgentState> agents;

	public NetworkState(ArrayList<AgentState> agents)
	{
		this.agents = agents;

	}

}
