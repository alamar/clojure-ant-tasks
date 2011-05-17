package com.ubermensch.ant.workaround;

public class StickyClassloaderTask extends org.apache.tools.ant.Task
{
	static
	{
		Thread.currentThread()
			.setContextClassLoader(StickyClassloaderTask.class.getClassLoader());
	}
}
