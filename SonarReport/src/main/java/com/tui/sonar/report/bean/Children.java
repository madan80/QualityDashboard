package com.tui.sonar.report.bean;

public class Children {
	private String name;

    private String[] children;

    private String[] elements;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String[] getChildren ()
    {
        return children;
    }

    public void setChildren (String[] children)
    {
        this.children = children;
    }

    public String[] getElements ()
    {
        return elements;
    }

    public void setElements (String[] elements)
    {
        this.elements = elements;
    }

    @Override
    public String toString()
    {
        return "Children [name = "+name+", children = "+children+", elements = "+elements+"]";
    }

}
