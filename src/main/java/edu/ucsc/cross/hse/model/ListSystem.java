package edu.ucsc.cross.hse.model;

import com.be3short.data.file.general.FileSystemInteractor;
import com.be3short.data.file.xml.XMLParser;
import edu.ucsc.cross.hse2.core.exe.operator.HybridEnvironment;
import edu.ucsc.cross.hse2.core.obj.data.DataSet;
import edu.ucsc.cross.hse2.core.obj.structure.HybridSystem;
import edu.ucsc.cross.hsec.tools.ui.resultview.PlotGenerator;
import java.io.File;

public class ListSystem extends HybridSystem<ListState>
{

	public ListSystem(ListState state)
	{
		super(state);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean C(ListState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void F(ListState x, ListState x_dot)
	{
		x_dot.listValueTimer = -1.0;
		for (ListElement e : x_dot.listElements)
		{
			if (x.listValueTimer < 0.5)
			{
				e.elementValue = 1.0;
			} else
			{
				// e.elementValue = -0.0;
			}
		}
	}

	@Override
	public boolean D(ListState x)
	{
		// TODO Auto-generated method stub
		return x.listValueTimer <= 0.0 || (x.listValueTimer >= x.listEvents);

	}

	@Override
	public void G(ListState x, ListState x_plus)
	{
		if (x.listValueTimer >= x.listEvents)
		{
			x_plus.listEvents = x.listEvents + 1.0;
		}
		if (x.listValueTimer <= 0.0)
		{
			x_plus.listValueTimer = x.listEvents;
			for (ListElement e : x_plus.listElements)
			{
				e.elementValue = e.elementValue + Math.random();
			}
		}
	}

	//
	public static void main(String args[])
	{
		ListState a = new ListState(50);
		ListSystem sys = new ListSystem(a);
		HybridEnvironment env = new HybridEnvironment();
		env.addContent(sys);

		env.start(true);
		// env.manager.resetData();

		// env.save("data/Dat");
		// mainz(args);
	}

	public static void mainz(String args[])
	{
		// HybridEnvironment env = HybridEnvironment.load(new File("data/Dat.gzip"));
		// DataSet dat = (DataSet) XMLParser.getObject(new File("data/Dat.xml"));

		HybridEnvironment env = HybridEnvironment.load(new File("data/Dat.hse"));
		env.start(false);
		PlotGenerator.openNewResultWindow(env.getManager().getDataCollector());

	}

}
