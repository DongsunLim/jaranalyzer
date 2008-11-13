package com.kirkk.analyzer.textui;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/*
 * Very simple ant task for the JarAnalyzer
 * http://www.kirkk.com/wiki/wiki.php/Main/JarAnalyzer
 */
public class JarAnalyzerTask extends Task
{
    private File srcdir;
    private File destfile;
    private String packageFilter;
    private String jarFilter;
    private String summaryclass;


	public void setJarFilter(String jarFilter) {
		this.jarFilter = jarFilter;
	}

    public void setPackageFilter(String packageFilter) {
		this.packageFilter = packageFilter;
	}

    public void setSrcdir(File srcdir)
    {
        this.srcdir = srcdir;
    }

    public void setDestfile(File destfile)
    {
        this.destfile = destfile;
    }

    public void setSummaryclass(String summaryclass)
    {
        this.summaryclass = summaryclass;
    }

    public void execute() throws BuildException
    {
        validateAttributes();
        try
        {
            Class clazz = Class.forName(summaryclass);
            Summary summary = (Summary)clazz.newInstance();
            summary.createSummary(srcdir, destfile, packageFilter, jarFilter);
            log(destfile + " successfully created");
        }
        catch(Exception exc)
        {
            throw new BuildException(exc);
        }
    }

    private void validateAttributes()
    {
        if(null == srcdir)
        {
            throw new BuildException("Missing srcdir");
        }
        if(null == destfile)
        {
            throw new BuildException("Missing destfile");
        }
        if(!(srcdir.exists() && srcdir.isDirectory()))
        {
            throw new BuildException("srcdir must be a valid directory");
        }
        if(destfile.isDirectory())
        {
            throw new BuildException("srcdir must be a file");
        }
        if(null == summaryclass)
        {
            throw new BuildException("Missing summaryclass");
        }
    }
}
