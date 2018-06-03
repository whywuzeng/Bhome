package com.system.bhouse.bhouse.Command;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Command
 */

public class GarageUpCommand implements Command {

    private GarageDoor mGarageDoor;

    public GarageUpCommand(GarageDoor mGarageDoor) {
        this.mGarageDoor = mGarageDoor;
    }

    @Override
    public void execute() {
        this.mGarageDoor.Up();
    }

    @Override
    public void Undo() {

    }
}
