package edu.ucsc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import net.openhft.compiler.CachedCompiler;

/**
 * Loads the addingStrategy and then after 3s replaces it with the subtractingStrategy.
 */
public class DynamicJavaClassLoading
{

	private final static String className = "test.MyClass";
	private final static String addingStrategy = "package test;\n" + "import test.DynamicJavaClassLoading.Strategy;\n"
	+ "public class MyClass implements Strategy {\n" + "    public int compute(int a, int b) {\n"
	+ "        return a+b;\n" + "    }\n" + "}\n";

	private final static String subtractingStrategy = "package test;\n"
	+ "import test.DynamicJavaClassLoading.Strategy;\n" + "public class MyClass implements Strategy {\n"
	+ "    public int compute(int a, int b) {\n" + "        return a-b;\n" + "    }\n" + "}\n";

	public static void main(String[] args) throws Exception
	{
		StrategyProxy strategy = new StrategyProxy();

		// Thread calling the strategy once a second
		Thread t = new Thread(() ->
		{
			while (true)
			{
				System.out.println(strategy.compute(10, 20));
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
		t.start();

		{
			ClassLoader cl = new ClassLoader()
			{
			};
			CachedCompiler cc = new CachedCompiler(null, null);
			Class aClass = cc.loadFromJava(cl, className, addingStrategy);

			Strategy runner = (Strategy) aClass.newInstance();
			strategy.setStratgey(runner);
		}

		Thread.sleep(3000);

		{
			ClassLoader cl = new ClassLoader()
			{
			};
			CachedCompiler cc = new CachedCompiler(null, null);
			Class aClass = cc.loadFromJava(cl, className, subtractingStrategy);

			Strategy runner = (Strategy) aClass.newInstance();
			strategy.setStratgey(runner);
		}
	}

	public interface Strategy
	{

		int compute(int a, int b);
	}

	public static class StrategyProxy implements Strategy
	{

		private volatile Strategy underlying;

		public void setStratgey(Strategy underlying)
		{
			this.underlying = underlying;
		}

		public int compute(int a, int b)
		{
			Strategy underlying = this.underlying;
			return underlying == null ? Integer.MIN_VALUE : underlying.compute(a, b);
		}
	}
}
