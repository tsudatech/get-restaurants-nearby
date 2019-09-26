package com.example.demoapplication;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.app.Activity;
import android.location.Location;

public class MyLocationListenerTest {

    @Test
    public void test_onLocationChanged_ifSynchronized() {
        // mock化
        MainActivity activity = mock(MainActivity.class);
        Location location = mock(Location.class);
        LocationService locationService = mock(LocationService.class);

        // listenerのspy定義
        MyLocationListener listener = spy(new MyLocationListener(activity, locationService));
        doAnswer(
                new Answer<String>() {
                    public String answer(InvocationOnMock invocation)  {
                        System.out.println("called");
                        return "Hello";
                    }
                }
        ).when(listener).requestResutaurantsInfo(any(LocationService.class), anyDouble(), anyDouble());

        // requestRestaurantInfo()がいくら並列走行させても必ず1回しか呼ばれないことを検査する。
        for(int i = 0 ; i < 1000; i++){
            // Thread.start()は複数回起動できないので、起動前に必ず新しいインスタンスを定義する
            Thread1 thread1 = new Thread1(listener, location);
            Thread1 thread2 = new Thread1(listener, location);
            clearInvocations(listener);
            listener.setActiveFlg(0);
            System.out.println("============================================================");
            // thread.run()ではなぜか並列処理が実行されなかった
            thread1.start();
            thread2.start();
            try{
                // 先にverifyが起動されてはいけないので、スレッドが終了するのをまつ
                thread1.join();
                thread2.join();
            }catch(InterruptedException ex){ }
            verify(listener, times(1)).requestResutaurantsInfo(any(LocationService.class), anyDouble(), anyDouble());
        }
    }
}

class Thread1 extends Thread {

    private MyLocationListener listener;
    private Location location;

    Thread1(MyLocationListener listener, Location location){
        this.listener = listener;
        this.location = location;
    }

    public void run(){
            listener.onLocationChanged(location);
    }
}

