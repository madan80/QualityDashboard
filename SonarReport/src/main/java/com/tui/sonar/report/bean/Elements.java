package com.tui.sonar.report.bean;

public class Elements {
	private String ratio;

    private String name;

    private String numerator;

    private String denominator;

    public String getRatio ()
    {
        return ratio;
    }

    public void setRatio (String ratio)
    {
        this.ratio = ratio;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getNumerator ()
    {
        return numerator;
    }

    public void setNumerator (String numerator)
    {
        this.numerator = numerator;
    }

    public String getDenominator ()
    {
        return denominator;
    }

    public void setDenominator (String denominator)
    {
        this.denominator = denominator;
    }

    @Override
    public String toString()
    {
        return "Elements [ratio = "+ratio+", name = "+name+", numerator = "+numerator+", denominator = "+denominator+"]";
    }

}
