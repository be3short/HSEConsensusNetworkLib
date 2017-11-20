package edu.ucsc.cross.hse.model.consensus.graph;

import org.jgrapht.EdgeFactory;

public class NetworkEdge implements EdgeFactory<Integer, NetworkEdge>
{

	Integer a;
	Integer b;

	public NetworkEdge(Integer a, Integer b)
	{
		this.a = a;
		this.b = b;
	}

	@Override
	public NetworkEdge createEdge(Integer arg0, Integer arg1)
	{
		// TODO Auto-generated method stub
		return new NetworkEdge(arg0, arg1);
	}
}
