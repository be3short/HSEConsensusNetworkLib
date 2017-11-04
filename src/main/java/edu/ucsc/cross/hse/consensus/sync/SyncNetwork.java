package edu.ucsc.cross.hse.consensus.sync;

import Jama.Matrix;
import com.be3short.data.cloning.ObjectCloner;
import edu.ucsc.cross.hse.core.environment.Environment;
import edu.ucsc.cross.hse.core.object.HybridSystem;
import edu.ucsc.cross.hse.models.network.syncconsensus.NetworkParams;
import java.util.ArrayList;

public class SyncNetwork extends HybridSystem<NetState>
{

	NetworkParams params;
	private Matrix stateLapValues;
	private Integer lastLap;
	public Integer eventt;
	private Double currentLap;
	public ArrayList<AgentSystem> nodes;

	public SyncNetwork(NetState state, NetworkParams params)
	{
		super(state);
		currentLap = 0.0;
		lastLap = -1;
		eventt = 0;
		stateLapValues = null;
		nodes = new ArrayList<AgentSystem>();
		this.params = params;
	}

	@Override
	public boolean C(NetState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean D(NetState x)
	{
		// TODO Auto-generated method stub
		return x.eventTimer <= 0.0;
	}

	@Override
	public void F(NetState x, NetState x_dot)
	{
		x_dot.eventTimer = -1.0;
		x_dot.elapsedTime = 1.0;

	}

	@Override
	public void G(NetState x, NetState x_plus)
	{
		if (D(x))
		{
			Double nextEvent = Math.random()
			* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
			+ params.minimumCommunicationInterval;
			// System.out.println("recomputing");
			double[][] stateValues = new double[nodes.size()][1];
			Integer i = 0;
			for (AgentSystem node : nodes)
			{
				stateValues[i][0] = node.getState().stateValue;
				i++;
			}
			Matrix stateVals = new Matrix(stateValues);
			Matrix lapCopy = (Matrix) ObjectCloner.xmlClone(params.laplacian);
			stateLapValues = lapCopy.times(stateVals);
			lastLap = eventt;
			x_plus.eventTimer = nextEvent;
			eventt++;
		}
	}

	public boolean commEvent()
	{
		return getState().eventTimer <= 0.0;
	}

	public double getLapValue(AgentSystem agent)
	{

		return stateLapValues.get(nodes.indexOf(agent), 0);
	}

	public static void generateRandomNetwork(Environment env, Integer num_nodes, Double state_range,
	Double controller_range)
	{

		NetworkParams paramz = NetworkParams.getWattsStrogatzParams(-.4, .3, 1.0, 1.0, num_nodes);
		NetState state = new NetState(1.1);
		SyncNetwork net = new SyncNetwork(state, paramz);
		env.add(net);
		for (int i = 0; i < num_nodes; i++)
		{
			NetworkAgentState node = new NetworkAgentState(((Math.random() - Math.random()) * state_range),
			((Math.random() - Math.random()) * controller_range));
			node.info().setName("N" + i);

			AgentSystem agentSys = new AgentSystem(node, paramz, net);
			env.add(agentSys);
			net.nodes.add(agentSys);
		}

	}

}
