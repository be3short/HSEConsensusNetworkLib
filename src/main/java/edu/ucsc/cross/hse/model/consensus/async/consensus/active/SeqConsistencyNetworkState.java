package edu.ucsc.cross.hse.model.consensus.async.consensus.active;

import edu.ucsc.cross.hse.core.object.ObjectSet;
import java.util.ArrayList;

public class SeqConsistencyNetworkState extends ObjectSet
{

	public ArrayList<SeqConsistencyAgentState> agents;
	public Double elapsed;
	public Double writeTimer;

	public SeqConsistencyNetworkState(ArrayList<SeqConsistencyAgentState> agents)
	{
		this.agents = agents;
		elapsed = 0.0;
		writeTimer = -1.0;

	}

}
