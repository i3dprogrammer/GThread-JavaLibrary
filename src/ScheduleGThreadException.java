/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public class ScheduleGThreadException extends Exception{
    
    public static final String ALIVE_THREAD_EXCEPTION_MESSAGE = "You set running thread in schedule group";
    public static final String TERMINATED_THREAD_EXCEPTION_MESSAGE = "You're set terminated thread in schedule group";
    
    public ScheduleGThreadException(){
        super();
    }
    public ScheduleGThreadException(String message){
        super(message);
    }
    public ScheduleGThreadException(Throwable cause){
        super(cause);
    }
    public ScheduleGThreadException(String message, Throwable cause){
        super(message, cause);
    }
}
