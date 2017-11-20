package edu.ucsc.cross.hse.model.consensus.graph;

import org.jgrapht.generate.WattsStrogatzGraphGenerator;

public class GraphGenerator
{

	public static NetworkGraph generateWattsStrogatzGraph(Integer n, Integer k, double p)
	{
		WattsStrogatzGraphGenerator<Integer, NetworkEdge> wsGen = new WattsStrogatzGraphGenerator<Integer, NetworkEdge>(
		n, k, p);
		VertexGenerator gen = new VertexGenerator();
		NetworkGraph graph = new NetworkGraph();
		wsGen.generateGraph(graph, gen, null);
		return graph;
	}
}