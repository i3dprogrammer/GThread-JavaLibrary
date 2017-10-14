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

public abstract class GThread<T> extends GThreadController implements GSheduler<T>{
    
    private static final String M_EMPTY_VALUE = "";
    private Thread mThread;
    private String mGThreadName;
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
        if(mThread != null)
            mThread.join();
    }
    
    public void join(long millis) throws InterruptedException{
        if(mThread != null)
            mThread.join(millis);
    }
    
    public void join(long millis, int nanos) throws InterruptedException{
        if(mThread != null)
            mThread.join(millis, nanos);
    }
    
    public void interrupt(){
        if(mThread != null)
            mThread.interrupt();
    }
    
    public boolean isAlive(){
        if(mThread != null)
            return mThread.isAlive();
        else
            return false;
    }
    
    public boolean isInterrupted(){
        if(mThread != null)
            return mThread.isInterrupted();
        else
            return false;
    }
    
    public String getName(){
        if(mGThreadName != null)
            return mGThreadName;
        else
            return M_EMPTY_VALUE;
    }
    
    public void setName(String name){
        mGThreadName = name;
    }
    
    public boolean isTerminated(){
        return mTerminated;
    }
}
