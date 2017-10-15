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

public abstract class GThreadController<T>{
    
    protected static final int G_THREAD_WITHOUT_SCHEDULE = 1;
    protected static final int G_THREAD_WITH_SCHEDULE = 2;
    protected static final int G_THREAD_WITH_LINKED_SCHEDULE = 3;
    
    private ScheduleGThread<T> mScheduleGThread;
  
    
    protected void notifyChanging(){
        if(mScheduleGThread != null)
            mScheduleGThread.onItemFinished();
    }
    
    protected void setScheduleThread(ScheduleGThread<T> scheduleGThread){
        mScheduleGThread = scheduleGThread;
    }
    
    protected int getGthreadType(){
        if(mScheduleGThread == null)
            return G_THREAD_WITH_SCHEDULE;
        else
            return G_THREAD_WITHOUT_SCHEDULE;
    }
}
