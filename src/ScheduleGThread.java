
import java.util.logging.Level;
import java.util.logging.Logger;

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


public abstract class ScheduleGThread<T>{
    // Number of worker when schedule is created.
    private static final int INTIAL_WORKERS_NUMBER = 0;
    // Decrease only one worker from the current workers.
    private static final int DECREASE_ONE_WORKER_FROM_WORKERS = -1;
    // Increase only one worker from the current workers.
    private static final int INCREASE_ONE_WORKER_FROM_WORKERS = 1;
    // Array of gthread which must have excuted as schedule way.
    private final GThread<T>[] M_GTHREADS_ARRAY;
    // Limitation number of workers which execute schedule tasks
    // at the same time.
    private final int M_WORKERS_LIMIT;
    // Original thread of Schedule gthread.
    private Thread mScheduleGThread; 
    // Number of current workers which execute tasks.
    private int mCurrentWorker;
    
    /**
     * ScheduleGThread constructor inwhich initialize an initial state of
     * schedule.
     * 
     * @param workers   Number of workers who are going to execute tasks
     * @param gThread   List of gthreads which must have execute as schedule way
     * @throws ScheduleGThreadException Exception throws when the list of gthreads
     *                                  contain threads which run before or is terminated
     */
    public ScheduleGThread(int workers, GThread<T>... gThread) throws ScheduleGThreadException{
        M_WORKERS_LIMIT = workers;
        M_GTHREADS_ARRAY = gThread;
        init();
    }
    /**
     * Identify gthreads to it's schedule and set initial value for current
     * workers.
     * 
     * @throws ScheduleGThreadException Exception throws when the list of gthreads
     *                                  contain threads which run before or is terminated
     */
    private void init(){
        for(final GThread<T> G_THREAD : M_GTHREADS_ARRAY)
            identifyGThread(G_THREAD);
        
        mCurrentWorker = INTIAL_WORKERS_NUMBER;
    }
    /**
     * Start schedule process. Check if all threads in idle mode or not.
     * 
     * @throws ScheduleGThreadException Exception throws when the list of gthreads
     *                                  contain threads which run before or is terminated
     */
    public void start() throws ScheduleGThreadException{
        checkGThreadValidation();

        mScheduleGThread = new Thread(() -> {
            for (GThread<T> M_GTHREADS_ARRAY1 : M_GTHREADS_ARRAY) {
                M_GTHREADS_ARRAY1.start();
                updateWorkers(INCREASE_ONE_WORKER_FROM_WORKERS);
                while(mCurrentWorker >= M_WORKERS_LIMIT);
            }
            
            allTasksExceuted();
        });
        mScheduleGThread.start();
    }
    /**
     * Called when each one of tasks is terminated.
     */
    public void onItemFinished(){
        updateWorkers(DECREASE_ONE_WORKER_FROM_WORKERS);        
    }
    /**
     * Synchronize process inwhich workers increase when
     * new task is avaliable and decrease when task is terminated.
     * @param workersChanger    State of workers changing
     */
    private synchronized void updateWorkers(int workersChanger){
       mCurrentWorker += workersChanger; 
    }
    /**
     * To sure there's no terminated gthread or running gthread is
     * Attached to shedule before running.
     * @throws ScheduleGThreadException 
     */
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
    /**
     * Set schedule as a parent to specific gthread.
     * @param gThread   Tasks which are going to execute in schedule
     */
    private void identifyGThread(GThread<T> gThread){
        gThread.setScheduleThread(this);
   }
    /**
     * insure that there's no task is work.
     */
    private void allTasksExceuted(){
      for(GThread<T> gThread : M_GTHREADS_ARRAY){
          try {
              gThread.join();
          } catch (InterruptedException ex) {
              Logger.getLogger(ScheduleGThread.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      onScheduleFinished();
    }
    
    /**
     * Called when schedule tasks are finished completely.
     */
    public abstract void onScheduleFinished();
}
