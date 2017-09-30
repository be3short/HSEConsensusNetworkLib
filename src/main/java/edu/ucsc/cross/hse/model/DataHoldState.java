package edu.ucsc.cross.hse.model;

import bs.commons.objects.access.FieldFinder;
import bs.commons.objects.access.Protected;
import com.be3short.data.cloning.ObjectCloner;
import edu.ucsc.cross.hse2.core.obj.structure.State;
import java.lang.reflect.Field;

public class DataHoldState<X> extends State
{

	public Double refreshTime;
	public X state;
	public X doubleState;
	public Double holdVal;
	// public TestState<X> t;

	public DataHoldState(X input)
	{
		refreshTime = 5.5 + 2.0 * Math.random();
		state = ObjectCloner.deepInstanceClone(input);
		doubleState = ObjectCloner.deepInstanceClone(input);
		try
		{
			holdVal = ((DataGenerationSystem) input).getState().data;
		} catch (Exception e)
		{
			holdVal = 0.0;
		}

	}
}
