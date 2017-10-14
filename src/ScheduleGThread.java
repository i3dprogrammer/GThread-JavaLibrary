
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
    
    private static final int INTIAL_WORKERS_NUMBER = 0;
    private static final int DECREASE_ONE_WORKER_FROM_WORKERS = -1;
    private static final int INCREASE_ONE_WORKER_FROM_WORKERS = 1;
    
    private final GThread<T>[] M_GTHREADS_ARRAY;
    private final int M_WORKERS_LIMIT;
    private final Thread M_SCHEDULE_THREAD; 
    private int mCurrentWorker;
    
    public ScheduleGThread(int workers, GThread<T>... gThread) throws SchedulGThreadException{
        M_GTHREADS_ARRAY = gThread;
        M_WORKERS_LIMIT = workers;
        M_SCHEDULE_THREAD = new Thread();

        init();
    }
    
    private void init() throws SchedulGThreadException{
        for(final GThread<T> G_THREAD : M_GTHREADS_ARRAY){
            G_THREAD.setScheduleThread(this);
        }
        checkGThreadValidation();
        mCurrentWorker = INTIAL_WORKERS_NUMBER;
    }
    
    public void start(){
        
    }
    
    public void onItemFinished(){
        
    }
    
    private synchronized void updateWorkers(int workersChanger){
       mCurrentWorker += workersChanger; 
    }
    
    private void checkGThreadValidation() throws SchedulGThreadException{
        for(GThread<T> gThread : M_GTHREADS_ARRAY){
            if(gThread.isAlive())
                throw new SchedulGThreadException(SchedulGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
            else if(gThread.isTerminated())
                throw new SchedulGThreadException(SchedulGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
        }
    }
}
