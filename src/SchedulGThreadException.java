/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public class SchedulGThreadException extends Exception{
    
    public static final String ALIVE_THREAD_EXCEPTION_MESSAGE = "You set running thread in schedule group";
    public static final String TERMINATED_THREAD_EXCEPTION_MESSAGE = "You're set terminated thread in schedule group";
    
    public SchedulGThreadException(){
        super();
    }
    public SchedulGThreadException(String message){
        super(message);
    }
    public SchedulGThreadException(Throwable cause){
        super(cause);
    }
    public SchedulGThreadException(String message, Throwable cause){
        super(message, cause);
    }
}
