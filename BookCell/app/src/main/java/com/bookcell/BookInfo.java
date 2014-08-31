package com.bookcell;

/**
 * Created by perry on 2014/8/31.
 */
public class BookInfo {
    private String name;
    private String describe;
    private int icon;
    BookInfo(String Name,String Describe,int Icon)
    {
        name = Name;
        describe = Describe;
        icon = Icon;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public String getDescribe()
    {
        return describe;
    }

    public void setDescribe(String describe)
    {
        this.describe = describe;
    }

}
