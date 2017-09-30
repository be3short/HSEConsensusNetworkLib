package edu.ucsc.cross.hse.model;

import edu.ucsc.cross.hse2.core.obj.format.HybridDynamics;
import edu.ucsc.cross.hse2.core.obj.structure.HybridSystem;

public class SampleAndHoldSystem<X> extends HybridSystem<DataHoldState<X>>
{

	public SampleAndHoldController<X> controller;

	public SampleAndHoldSystem(DataHoldState<X> model, SampleAndHoldController<X> controller)
	{
		super(model);
		this.controller = controller;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void F(DataHoldState<X> x, DataHoldState<X> x_dot)
	{
		x_dot.refreshTime = -1.0;

	}

	@Override
	public boolean C(DataHoldState<X> x)
	{
		return x.refreshTime >= 0.0;

	}

	@Override
	public void G(DataHoldState<X> x, DataHoldState<X> x_plus)
	{
		x_plus.refreshTime = controller.u().refreshTime;
		x_plus.state = controller.u().state;
		x_plus.doubleState = controller.u().doubleState;
		x_plus.holdVal = controller.u().holdVal;
	}

	@Override
	public boolean D(DataHoldState<X> x)
	{
		return x.refreshTime <= 0.0;
	}

}
