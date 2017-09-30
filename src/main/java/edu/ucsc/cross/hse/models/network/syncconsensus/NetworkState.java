package edu.ucsc.cross.hse.models.network.syncconsensus;

import Jama.Matrix;
import edu.ucsc.cross.hse2.core.obj.structure.State;
import java.util.ArrayList;

public class NetworkState extends State
{

	public ArrayList<NodeState> nodes;
	public double eventTimer;

	public NetworkState(ArrayList<NodeState> nodez, double event_timer)
	{
		nodes = nodez;
		eventTimer = event_timer;
	}

	public Integer totalNodes()
	{
		return nodes.size();
	}
}
