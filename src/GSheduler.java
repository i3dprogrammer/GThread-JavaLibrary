/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public interface GSheduler<T> {
    abstract void onFinished(T object);
    abstract T onProgress();
}
