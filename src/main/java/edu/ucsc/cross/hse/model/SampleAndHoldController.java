package edu.ucsc.cross.hse.model;

import edu.ucsc.cross.hse2.core.obj.format.Controller;

public class SampleAndHoldController<U> implements Controller<DataHoldState<U>>
{

	public U input;

	public SampleAndHoldController(U input)
	{
		this.input = input;
	}

	@Override
	public DataHoldState<U> u()
	{
		// TODO Auto-generated method stub
		return new DataHoldState<U>(input);
	}

}
