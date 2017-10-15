
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/*
 * Copyright [2017] Mohamed Nagy Mostafa Mohamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *
 * @author mohamednagy
 */
public class ScheduleGThread<T>{
    
    private static final int INTIAL_WORKERS_NUMBER = 0;
    private static final int PROCESS_IS_NOT_SUCCESS = -1;
    private static final int DECREASE_ONE_WORKER_FROM_WORKERS = -1;
    private static final int INCREASE_ONE_WORKER_FROM_WORKERS = 1;
    
    private final ArrayList<GThread<T>> M_GTHREADS_ARRAY;
    private final int M_WORKERS_LIMIT;
    private Thread mScheduleGThread; 
    private int mCurrentWorker;
    private boolean mScheduleGThreadRunningState;
    
    public ScheduleGThread(int workers, GThread<T>... gThread) throws ScheduleGThreadException{
        M_WORKERS_LIMIT = workers;
        M_GTHREADS_ARRAY = new ArrayList<>();
        mScheduleGThreadRunningState = false;
        init(gThread);
    }
    
    private void init(GThread<T>... gThread) throws ScheduleGThreadException{
        for(final GThread<T> G_THREAD : gThread)
            identifyGThread(G_THREAD);
        
        checkGThreadValidation();
        mCurrentWorker = INTIAL_WORKERS_NUMBER;
    }
    
    public void start(){
        mScheduleGThreadRunningState = true;
        
        mScheduleGThread = new Thread(() -> {
            for (GThread<T> M_GTHREADS_ARRAY1 : M_GTHREADS_ARRAY) {
                M_GTHREADS_ARRAY1.start();
                updateWorkers(INCREASE_ONE_WORKER_FROM_WORKERS);
                while(mCurrentWorker >= M_WORKERS_LIMIT);
            }
        });
        mScheduleGThread.start();
    }
    
    public int add(GThread<T> gThread) throws ScheduleGThreadException{
        if(mScheduleGThreadRunningState){
            identifyGThread(gThread);
              
            if(!mScheduleGThread.isAlive()){
                start();
            }          
            return M_GTHREADS_ARRAY.size() - 1;
        }else{
            return PROCESS_IS_NOT_SUCCESS;
        }
    }
    
    public void remove(int index){
        M_GTHREADS_ARRAY.remove(index);
    }
    
    public boolean isGThreadAliveAt(int index){
        if(M_GTHREADS_ARRAY.size() > index){
            return M_GTHREADS_ARRAY.get(index).isAlive();
        }else{
            return false;
        }
    }
    
    public int gthreadInTaskNumbers(){
        return M_GTHREADS_ARRAY.size() -1;
    }
   
    public int currentRunning(){
        return mCurrentWorker;
    }
    
    public void onItemFinished(int id){
        updateWorkers(DECREASE_ONE_WORKER_FROM_WORKERS);
        M_GTHREADS_ARRAY.remove(id);
        
    }
    
    private synchronized void updateWorkers(int workersChanger){
       mCurrentWorker += workersChanger; 
    }
    
    private void checkGThreadValidation() throws ScheduleGThreadException{
        for(GThread<T> gThread : M_GTHREADS_ARRAY){
            if(gThread.isAlive())
                throw new ScheduleGThreadException(ScheduleGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
            else if(gThread.isTerminated())
                throw new ScheduleGThreadException(ScheduleGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
        }
    }
    
    private void identifyGThread(GThread<T> gThread){
        gThread.setScheduleThread(this, M_GTHREADS_ARRAY.size());
        M_GTHREADS_ARRAY.add(gThread);
    }
}
