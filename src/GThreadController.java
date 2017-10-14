/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public abstract class GThreadController<T>{
    
    private ScheduleGThread<T> mScheduleGThread;

    
    protected void notifyChanging(){
        if(mScheduleGThread != null)
            mScheduleGThread.onItemFinished();
    }
    
    protected void setScheduleThread(ScheduleGThread<T> scheduleGThread){
        mScheduleGThread = scheduleGThread;
    }
}
