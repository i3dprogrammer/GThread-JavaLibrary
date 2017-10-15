
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        GThread<String> t1 = new GThread<String>() {
            @Override
            public void onFinished(String object) {
                Util.println("finished" + object);
            }

            @Override
            public String onProgress() {
                Util.println("t1 working");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t1";
            }
        };
        
        GThread<String> t2 = new GThread<String>() {
            @Override
            public void onFinished(String object) {
                Util.println("finished" + object);
            }

            @Override
            public String onProgress() {
                Util.println("t2 working");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t2";
            }
        };
        
        GThread<String> t3 = new GThread<String>() {
            @Override
            public void onFinished(String object) {
                Util.println("finished" + object);
            }

            @Override
            public String onProgress() {
                Util.println("t3 working");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t3";
            }
        };
        
        GThread<String> t4 = new GThread<String>() {
            @Override
            public void onFinished(String object) {
                Util.println("finished" + object);
            }

            @Override
            public String onProgress() {
                Util.println("t4 working");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t4";
            }
        };
        
        GThread<String> t5 = new GThread<String>() {
            @Override
            public void onFinished(String object) {
                Util.println("finished" + object);
            }

            @Override
            public String onProgress() {
                Util.println("t5 working");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t5";
            }
        };
        
        GThread<String> t6 = new GThread<String>() {
            @Override
            public void onFinished(String object) {
                Util.println("finished" + object);
            }

            @Override
            public String onProgress() {
                Util.println("t6 working");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t6";
            }
        };
        
        GThread<String> t7 = new GThread<String>() {
            @Override
            public void onFinished(String object) {
                Util.println("finished" + object);
            }

            @Override
            public String onProgress() {
                Util.println("t7 working");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "t7";
            }
        };
        
        try {
            ScheduleGThread<String> scheduleGThread = new ScheduleGThread<>(3,t1, t2);
            scheduleGThread.start();
            Thread.sleep(1000);
            scheduleGThread.add(t3);
            Util.println("tasks : " + String.valueOf(scheduleGThread.gthreadInTaskNumbers()));
            Thread.sleep(2000);
            Util.println("tasks : " + String.valueOf(scheduleGThread.gthreadInTaskNumbers()));
            scheduleGThread.add(t4);
            scheduleGThread.add(t5);
            scheduleGThread.add(t6);
            Util.println("tasks : " + String.valueOf(scheduleGThread.gthreadInTaskNumbers()));
            Thread.sleep(6000);
            scheduleGThread.add(t7);
            Util.println("tasks : " + String.valueOf(scheduleGThread.gthreadInTaskNumbers()));

        } catch (ScheduleGThreadException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
