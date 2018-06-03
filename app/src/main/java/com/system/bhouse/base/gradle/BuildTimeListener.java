//package com.system.bhouse.base.gradle;
//
///**
// * Created by Administrator on 2018-06-01.
// * <p>
// * by author wz
// * <p>
// * com.system.bhouse.base.gradle
// */
//
//public class BuildTimeListener implements TaskExecutionListener, BuildListener {
//        private Clock clock
//        private times = []
//
//        @Override
//        void beforeExecute(Task task) {
//            clock = new org.gradle.util.Clock()
//        }
//
//        @Override
//        void afterExecute(Task task, TaskState taskState) {
//            def ms = clock.timeInMs
//            times.add([ms, task.path])
//
//            //task.project.logger.warn "${task.path} spend ${ms}ms"
//        }
//
//        @Override
//        void buildFinished(BuildResult result) {
//            println "Task spend time:"
//            for (time in times) {
//                if (time[0] >= 50) {
//                    printf "%7sms  %s\n", time
//                }
//            }
//        }
//
//}
