package edu.ucsc.cross.hse.model.consensus.graph;

import org.jgrapht.EdgeFactory;

public class NetworkEdgeFactory implements EdgeFactory<Integer, NetworkEdge>
{

	@Override
	public NetworkEdge createEdge(Integer arg0, Integer arg1)
	{
		// TODO Auto-generated method stub
		return new NetworkEdge(arg0, arg1);
	}

}
