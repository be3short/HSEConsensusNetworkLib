package edu.ucsc.cross.hse.model;

import edu.ucsc.cross.hse2.core.obj.structure.State;
import java.util.ArrayList;

public class ListState extends State
{

	public double listValueTimer;
	public double listEvents;
	public ArrayList<ListElement> listElements;

	public ListState(Integer num_elements)
	{
		listElements = new ArrayList<ListElement>();
		listValueTimer = 0.0;
		listEvents = 0.0;
		for (Integer i = 0; i < num_elements; i++)
		{

			ListElement ele = new ListElement();
			listElements.add(ele);
			ele.setName("Element " + i);
		}
	}
}
