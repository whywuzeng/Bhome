package com.system.bhouse.bhouse;

import com.system.bhouse.bhouse.Command.Light;
import com.system.bhouse.bhouse.Command.LightOffCommand;
import com.system.bhouse.bhouse.Command.LightOnCommand;
import com.system.bhouse.bhouse.Command.SimpleRemoteControl;

import org.junit.Test;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */

public class RunSubject {
    @Test
    public void addition_isCorrect() throws Exception {
//        ChicagoStylePizaaStore chicagoStylePizaaStore = new ChicagoStylePizaaStore();
//        chicagoStylePizaaStore.OrderPizza("Cheese");
//
//        NYPizzaStyleStore nyPizzaStyleStore = new NYPizzaStyleStore();
//        nyPizzaStyleStore.OrderPizza("Clam");
        Light light = new Light();
        LightOnCommand lightCommand = new LightOnCommand(light);
        SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();
        simpleRemoteControl.setCommand(lightCommand);
        simpleRemoteControl.performOn();

        //调用这个 lightoff 这个命令
        LightOffCommand lightOffCommand = new LightOffCommand(light);
        simpleRemoteControl.setCommand(lightOffCommand);
        simpleRemoteControl.performOn();
    }
}
