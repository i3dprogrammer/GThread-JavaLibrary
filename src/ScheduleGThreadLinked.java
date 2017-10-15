
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
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
    
    private GQueueLinkedList<GThread<T>> mQueueLinkedList;
    private int mSheduleLinkState;
    
    public ScheduleGThreadLinked(int workers, GThread<T>... gThread){
        super(workers, gThread);
        init(gThread);
    }
    
    private void init(GThread<T>... gThread){
        mQueueLinkedList = new GQueueLinkedList<>();
        mSheduleLinkState = SCHEDULE_LINK_TASKS_IDLE;
        identifyGThreads(gThread);
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
            
            while(mQueueLinkedList.hasNext(mQueueLinkedList.iterator())){
                GThread<T> gThread = mQueueLinkedList.poll();
                gThread.setScheduleGThreadLinked(this,gThread);
                gThread.start();
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
            mQueueLinkedList.add(gThread);
            checkAddingGThread(gThread);
        } catch (ScheduleGThreadException ex) {
            Logger.getLogger(ScheduleGThreadLinked.class.getName()).log(Level.SEVERE, null, ex);
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }
                
        return SCHEDULE_LINK_ACCEPT_RESPONSE;
    }
    
    public int remove(GThread<T> gThread){
        if(mQueueLinkedList.contains(gThread)){
            if(!gThread.isAlive()){
                mQueueLinkedList.remove(gThread);
                                
                return SCHEDULE_LINK_ACCEPT_RESPONSE;
            }else{
                return SCHEDULE_LINK_REJECT_RESPONSE;
            }
        }else{
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }
    }
    
    private void checkAddingGThread(GThread<T> gThread){
        if(mSheduleLinkState == SCHEDULE_LINK_TASKS_FINISHED && !gThread.isAlive()){
                start();                
        }
    }
    /**
     * Called when each one of tasks is terminated.
     * @param gthreadID 
     */
    public void onItemFinished(GThread<T> gThread){
        mQueueLinkedList.remove(gThread);
        updateWorkers(DECREASE_ONE_WORKER_FROM_WORKERS);        
    }

    private void checkGThreadValidationAt(GThread<T> gThread) throws ScheduleGThreadException {
        switch(gThread.gthreadState()){
                case GThread.G_THREAD_RUNNING:
                    throw new ScheduleGThreadException(ScheduleGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
                case GThread.G_THREAD_TERMINATED:
                    throw new ScheduleGThreadException(ScheduleGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
            }
    }
    
    private void identifyGThreads(GThread<T>... gThreads){
        for(GThread<T> gThread : gThreads){
            mQueueLinkedList.add(gThread);
        }
    }
       
}
