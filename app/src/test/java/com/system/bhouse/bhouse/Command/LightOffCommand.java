package com.system.bhouse.bhouse.Command;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Command
 */

public class LightOffCommand implements Command {

    public LightOffCommand(Light mLight) {
        this.mLight = mLight;
    }

    private Light mLight;


    @Override
    public void execute() {
        mLight.Off();
    }

    @Override
    public void Undo() {
        mLight.On();
    }
}
