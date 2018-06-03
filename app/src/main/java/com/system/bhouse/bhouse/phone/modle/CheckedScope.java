// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 

package com.system.bhouse.bhouse.phone.modle;


public class CheckedScope
{

    public CheckedScope()
    {
    }

    public int getAccuracy()
    {
        return accuracy;
    }

    public double getLat()
    {
        return lat;
    }

    public double getLng()
    {
        return lng;
    }

    public String getName()
    {
        return name;
    }

    public void setAccuracy(int i)
    {
        accuracy = i;
    }

    public void setLat(double d)
    {
        lat = d;
    }

    public void setLng(double d)
    {
        lng = d;
    }

    public void setName(String s)
    {
        name = s;
    }

    public int accuracy;
    public double lat;
    public double lng;
    private String name;
}
