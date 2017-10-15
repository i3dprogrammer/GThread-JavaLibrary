
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public abstract class ScheduleGThreadLinked<T> extends ScheduleGThread<T>{

    public ScheduleGThreadLinked(int workers, GThread<T>... gThread) throws ScheduleGThreadException {
        super(workers, gThread);
    }
    
   
}
