package stan.reactive.sample;

import stan.reactive.sample.notify.NotifySample;
import stan.reactive.sample.single.SingleSample;
import stan.reactive.sample.stream.StreamSample;

public class App
{
    static public void main(String[] args)
    {
        System.out.println("\n\tSample NotifyObservable");
        NotifySample.sampleNotifyObservable();
        System.out.println("\n\tSample SingleObservable");
        SingleSample.sampleSingleObservable();
        System.out.println("\n\tSample StreamObservable");
        StreamSample.sampleStreamObservable();
        System.out.println("\n\tSample SingleObservable Map operator");
        SingleSample.sampleSingleObservableMap();
        System.out.println("\n\tSample SingleObservable Flat operator");
        SingleSample.sampleSingleObservableFlat();
        System.out.println("\n\tSample SingleObservable Chain operator");
        SingleSample.sampleSingleObservableChain();
        System.out.println("\n\tSample SingleObservable Merge operator");
        SingleSample.sampleSingleObservableMerge();
    }
}