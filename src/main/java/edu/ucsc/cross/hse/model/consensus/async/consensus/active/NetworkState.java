package edu.ucsc.cross.hse.model.consensus.async.consensus.active;

import edu.ucsc.cross.hse.core.object.Objects;
import java.util.ArrayList;

public class NetworkState extends Objects
{

	public ArrayList<AgentState> agents;
	public Double elapsed;
	public Double writeTimer;

	public NetworkState(ArrayList<AgentState> agents)
	{
		this.agents = agents;
		elapsed = 0.0;
		writeTimer = -1.0;

	}

}
