
import java.util.ArrayList;
import java.util.HashMap;
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
public class ScheduleGThreadLinked<T> extends GShedule<T>{
    public static final int SCHEDULE_LINK_ACCEPT_RESPONSE  = 1;
    public static final int SCHEDULE_LINK_REJECT_RESPONSE = -1;
    public static final int SCHEDULE_LINK_TASKS_FINISHED = 4;
    public static final int SCHEDULE_LINK_TASKS_RUNNING = 2;
    public static final int SCHEDULE_LINK_TASKS_IDLE = 3;
    
    private HashMap<Long, GThread<T>> mGThreadHashMapTasks;
    private long mGThreadIdCode = 0;
    private int mSheduleLinkState;
    
    public ScheduleGThreadLinked(int workers, GThread<T>... gThread) throws ScheduleGThreadException {
        super(workers, gThread);
        mSheduleLinkState = SCHEDULE_LINK_TASKS_IDLE;
        init(gThread);
    }
    
    private void init(GThread<T>... gThread){
        identifyGThreadAsTasks(gThread);
    }

    @Override
    protected int start() {
        mSheduleLinkState = SCHEDULE_LINK_TASKS_RUNNING;
        try {
            checkGThreadValidation();
        } catch (ScheduleGThreadException ex) {
            Logger.getLogger(ScheduleGThreadLinked.class.getName()).log(Level.SEVERE, null, ex);
            return GShedule.G_SCHEDULE_START_FAILED;
        }
        
        mScheduleGThread = new Thread(() -> {
            for (GThread<T> M_GTHREADS_ARRAY1 : M_GTHREADS_ARRAY) {
                M_GTHREADS_ARRAY1.start();
                updateWorkers(INCREASE_ONE_WORKER_FROM_WORKERS);
                while(mCurrentWorker >= M_WORKERS_LIMIT);
            }
            mSheduleLinkState = SCHEDULE_LINK_TASKS_FINISHED;

            
        });
        mScheduleGThread.start();
        
        return GShedule.G_SCHEDULE_START_SUCCESSFULLY;
    }
    
    public int add(GThread<T> gThread){
        try {
            checkGThreadValidationAt(gThread);
            mGThreadHashMapTasks.put(mGThreadIdCode++, gThread);
            checkAddingGThread();
        } catch (ScheduleGThreadException ex) {
            Logger.getLogger(ScheduleGThreadLinked.class.getName()).log(Level.SEVERE, null, ex);
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }
                
        return SCHEDULE_LINK_ACCEPT_RESPONSE;
    }
    
    public int remove(long gthreadID){
        if(mGThreadHashMapTasks.containsKey(gthreadID)){
            if(!mGThreadHashMapTasks.get(gthreadID).isAlive()){
                mGThreadHashMapTasks.remove(gthreadID);
                                
                return SCHEDULE_LINK_ACCEPT_RESPONSE;
            }else{
                return SCHEDULE_LINK_REJECT_RESPONSE;
            }
        }else{
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }
    }
    
    private void checkAddingGThread(){
        if(mSheduleLinkState == SCHEDULE_LINK_TASKS_FINISHED){
                start();                
        }
    }
    /**
     * Called when each one of tasks is terminated.
     * @param gthreadID 
     */
    public void onItemFinished(long gthreadID){
        mGThreadHashMapTasks.remove(mGThreadHashMapTasks.containsKey(gthreadID));
        updateWorkers(DECREASE_ONE_WORKER_FROM_WORKERS);        
    }
    
    private void identifyGThreadAsTasks(GThread<T>... gThreads){
        for(GThread<T> gThread: gThreads){
            mGThreadHashMapTasks.put(mGThreadIdCode++, gThread);
        }
    }

    private void checkGThreadValidationAt(GThread<T> gThread) throws ScheduleGThreadException {
        switch(gThread.gthreadState()){
                case GThread.G_THREAD_RUNNING:
                    throw new ScheduleGThreadException(ScheduleGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
                case GThread.G_THREAD_TERMINATED:
                    throw new ScheduleGThreadException(ScheduleGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
            }
    }
    
    
   
}
