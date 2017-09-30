package edu.ucsc.cross.hse.model;

import com.jcabi.aspects.Loggable;
import edu.ucsc.cross.hse2.core.exe.operator.HybridEnvironment;
import edu.ucsc.cross.hse2.core.obj.format.HybridDynamics;
import edu.ucsc.cross.hse2.core.obj.structure.HybridSystem;

public class DataGenerationSystem extends HybridSystem<DataGenerationState>
{

	public DataGenerationSystem(DataGenerationState model)
	{
		super(model);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void F(DataGenerationState x, DataGenerationState x_dot)
	{
		x_dot.timer = -1.0;
		x_dot.data = 0.0;
	}

	@Override
	public boolean C(DataGenerationState x)
	{
		return true;
	}

	@Override
	public void G(DataGenerationState x, DataGenerationState x_plus)
	{
		x_plus.timer = 2.0;
		x_plus.data = x.data + 1.0;
	}

	@Override
	public boolean D(DataGenerationState x)
	{
		return x.timer <= 0.0;
	}

	public static void main(String args[])
	{
		DataGenerationState st = new DataGenerationState(10.0, 20.0);
		DataGenerationSystem s = new DataGenerationSystem(st);
		DataHoldState<DataGenerationSystem> hs = new DataHoldState<DataGenerationSystem>(s);
		SampleAndHoldController<DataGenerationSystem> c = new SampleAndHoldController<DataGenerationSystem>(s);
		HybridEnvironment env = new HybridEnvironment();
		SampleAndHoldSystem<DataGenerationSystem> sss = new SampleAndHoldSystem<DataGenerationSystem>(hs, c);
		env.addContent(s, sss);
		env.start(true);
		int i = 1;
		for (String arg : args)
		{
			System.out.println(i++ + " : " + args);
		}
	}
}
