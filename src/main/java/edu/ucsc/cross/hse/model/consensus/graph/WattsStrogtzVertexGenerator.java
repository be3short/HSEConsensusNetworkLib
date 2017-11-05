package edu.ucsc.cross.hse.model.consensus.graph;

import org.jgrapht.VertexFactory;

public class WattsStrogtzVertexGenerator implements VertexFactory<Integer>
{

	Integer vertex = 0;

	public WattsStrogtzVertexGenerator()
	{

	}

	@Override
	public Integer createVertex()
	{
		// TODO Auto-generated method stub
		return vertex++;
	}

}
