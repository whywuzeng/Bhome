package com.system.bhouse.bhouse.Command;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Command
 */

public class CeilingFanOffCommand implements Command{

    private CeilingFan mCeilingFan;

    private int Speed;

    public CeilingFanOffCommand(CeilingFan ceilingFan){
        this.mCeilingFan=ceilingFan;
    }

    @Override
    public void execute() {
        Speed=mCeilingFan.getLevel();
        mCeilingFan.off();
    }

    @Override
    public void Undo() {
        if (Speed == CeilingFan.HIGH) {
            mCeilingFan.high();
        }
        else if (Speed == CeilingFan.MEDIUM) {
            mCeilingFan.medium();
        }
        else if (Speed == CeilingFan.LOW) {
            mCeilingFan.low();
        }
    }
}
