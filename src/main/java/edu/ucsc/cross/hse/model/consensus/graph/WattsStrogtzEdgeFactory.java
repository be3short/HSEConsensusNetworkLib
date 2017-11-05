package edu.ucsc.cross.hse.model.consensus.graph;

import org.jgrapht.EdgeFactory;

public class WattsStrogtzEdgeFactory implements EdgeFactory<Integer, Edge>
{

	@Override
	public Edge createEdge(Integer arg0, Integer arg1)
	{
		// TODO Auto-generated method stub
		return new Edge(arg0, arg1);
	}

}
