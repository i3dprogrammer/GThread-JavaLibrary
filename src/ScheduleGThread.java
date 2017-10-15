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
public abstract class ScheduleGThread<T>{
    
    private static final int INTIAL_WORKERS_NUMBER = 0;
    private static final int DECREASE_ONE_WORKER_FROM_WORKERS = -1;
    private static final int INCREASE_ONE_WORKER_FROM_WORKERS = 1;
    
    private final GThread<T>[] M_GTHREADS_ARRAY;
    private final int M_WORKERS_LIMIT;
    private Thread mScheduleGThread; 
    private int mCurrentWorker;
    
    public ScheduleGThread(int workers, GThread<T>... gThread) throws ScheduleGThreadException{
        M_WORKERS_LIMIT = workers;
        M_GTHREADS_ARRAY = gThread;
        init();
    }
    
    private void init() throws ScheduleGThreadException{
        for(final GThread<T> G_THREAD : M_GTHREADS_ARRAY)
            identifyGThread(G_THREAD);
        
        checkGThreadValidation();
        mCurrentWorker = INTIAL_WORKERS_NUMBER;
    }
    
    public void start(){
        
        mScheduleGThread = new Thread(() -> {
            for (GThread<T> M_GTHREADS_ARRAY1 : M_GTHREADS_ARRAY) {
                M_GTHREADS_ARRAY1.start();
                updateWorkers(INCREASE_ONE_WORKER_FROM_WORKERS);
                while(mCurrentWorker >= M_WORKERS_LIMIT);
            }
        });
        mScheduleGThread.start();
    }
   
    
    public void onItemFinished(){
        updateWorkers(DECREASE_ONE_WORKER_FROM_WORKERS);        
    }
    
    private synchronized void updateWorkers(int workersChanger){
       mCurrentWorker += workersChanger; 
    }
    
    private void checkGThreadValidation() throws ScheduleGThreadException{
        for(GThread<T> gThread : M_GTHREADS_ARRAY){
            switch(gThread.gthreadState()){
                case GThread.G_THREAD_RUNNING:
                    throw new ScheduleGThreadException(ScheduleGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
                case GThread.G_THREAD_TERMINATED:
                    throw new ScheduleGThreadException(ScheduleGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
            }
        }
    }
    
    private void identifyGThread(GThread<T> gThread){
        gThread.setScheduleThread(this);
   }
    
    
    public abstract void onScheduleFinished(GThread<T>[] gThreads);
}
