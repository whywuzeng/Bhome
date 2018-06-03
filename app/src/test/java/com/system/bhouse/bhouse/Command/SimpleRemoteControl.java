package com.system.bhouse.bhouse.Command;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Command
 */

public class SimpleRemoteControl {

    private Command mLight;

    public void setCommand(Command command) {
        this.mLight = command;
    }

    public void performOn() {
        mLight.execute();
    }

    public void ButtonUndo(){
        mLight.Undo();
    }
}
