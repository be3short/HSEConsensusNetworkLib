package edu.ucsc.cross.hse.model;

import edu.ucsc.cross.hse2.core.obj.structure.State;

public class DataGenerationState extends State
{

	public Double timer;
	public Double data;
	// public DGC con;

	public DataGenerationState(Double timer, Double data)// , DataGenerationState sta)
	{
		this.timer = timer;
		this.data = data;
		// con = new DGC(sta);
	}
}
