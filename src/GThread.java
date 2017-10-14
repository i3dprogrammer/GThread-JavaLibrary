
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
public abstract class GThread<T>extends GThreadController implements GSheduler<T>{
    
    
    public void start(){
        handlingProgress();
    }
    
    private void handlingProgress(){ 
        new Thread(()->{
            T object = onProgress();
            notifyChanging();
            onFinished(object);
        }).start();
    }
    
    
}
