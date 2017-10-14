/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public abstract class GThread<T> extends GThreadController implements GSheduler<T>{
    
    private Thread mThread;
    private boolean mTerminated;
    
    public GThread(){
        mTerminated = false;
    }
    
    public void start(){
        handlingProgress();
    }
    
    private void handlingProgress(){ 
        mThread = new Thread(()->{
            T object = onProgress();
            notifyChanging();
            mTerminated = true;
            onFinished(object);
        });
        mThread.start();
    }
    
    public void join() throws InterruptedException{
        mThread.join();
    }
    
    public void join(long millis) throws InterruptedException{
        mThread.join(millis);
    }
    
    public void join(long millis, int nanos) throws InterruptedException{
        mThread.join(millis, nanos);
    }
    
    public void interrupt(){
        mThread.interrupt();
    }
    
    public boolean isAlive(){
        return mThread.isAlive();
    }
    
    public boolean isInterrupted(){
        return mThread.isInterrupted();
    }
    
    public String getName(){
        return mThread.getName();
    }
    
    public void setName(String name){
        mThread.setName(name);
    }
    
    public boolean isTerminated(){
        return mTerminated;
    }
}
