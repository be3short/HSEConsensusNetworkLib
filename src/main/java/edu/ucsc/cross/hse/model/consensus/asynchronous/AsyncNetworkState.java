package edu.ucsc.cross.hse.model.consensus.asynchronous;

import edu.ucsc.cross.hse.core.object.ObjectSet;
import java.util.ArrayList;

public class AsyncNetworkState extends ObjectSet
{

	public ArrayList<AsyncAgentState> agents;

	public AsyncNetworkState(ArrayList<AsyncAgentState> agents)
	{
		this.agents = agents;
	}
}
