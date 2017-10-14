
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
public class ScheduleGThread<T>{
    
    private GThread<T>[] mGThreadsArray;
    private int mWorkersLimit;
    
    public ScheduleGThread(int workers, GThread<T>... gThread){
        mGThreadsArray = gThread;
        mWorkersLimit = workers;
    }
    
    public void start(){
        
    }
    
    public void onItemFinished(){
        
    }
}
