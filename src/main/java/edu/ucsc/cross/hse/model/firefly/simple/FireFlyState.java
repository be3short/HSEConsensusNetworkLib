package edu.ucsc.cross.hse.model.firefly.simple;

import edu.ucsc.cross.hse2.core.obj.structure.State;

public class FireFlyState extends State
{

	public double glowIntensity;

	public FireFlyState(double glow_intensity)
	{
		this.glowIntensity = glow_intensity;
	}
}
