package edu.ucsc.cross.hse.model.consensus.async.consensus;

import edu.ucsc.cross.hse.core.object.ObjectSet;
import java.util.ArrayList;

public class AsyncConsistencyNetworkState extends ObjectSet
{

	public ArrayList<AsyncConsistencyAgentState> agents;

	public AsyncConsistencyNetworkState(ArrayList<AsyncConsistencyAgentState> agents)
	{
		this.agents = agents;

	}

}
