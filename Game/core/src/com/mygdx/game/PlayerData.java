package com.mygdx.game;

public class PlayerData {
    public String name;
    public int score;

    public PlayerData()
    {

    }

    public PlayerData(String name, int score)
    {
        this.name = name;

        this.score = score;
    }

    public void printData()
    {
        System.out.println(name + score);
    }
}
