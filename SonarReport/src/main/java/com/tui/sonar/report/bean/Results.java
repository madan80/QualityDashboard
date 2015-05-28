/**
 * 
 */
package com.tui.sonar.report.bean;

/**
 * @author machou
 *
 */
public class Results {
	private String name;

    private Children[] children;

    private Elements[] elements;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Children[] getChildren ()
    {
        return children;
    }

    public void setChildren (Children[] children)
    {
        this.children = children;
    }

    public Elements[] getElements ()
    {
        return elements;
    }

    public void setElements (Elements[] elements)
    {
        this.elements = elements;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", children = "+children+", elements = "+elements+"]";
    }

}
