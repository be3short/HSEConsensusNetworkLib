package edu.ucsc.cross.hse.consensus.sync;

import edu.ucsc.cross.hse.core.object.Objects;

public class NetState extends Objects
{

	public Double eventTimer;
	public Double elapsedTime;

	public NetState(Double event_timer)
	{
		elapsedTime = 0.0;
		eventTimer = event_timer;
	}

}
