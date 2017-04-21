package stan.reactive.sample.stream;

import stan.reactive.stream.StreamObservable;
import stan.reactive.stream.StreamObserver;

public class StreamSample
{
    static public void sampleStreamObservable()
    {
        successStreamObservable().subscribe(new StreamObserver<Integer>()
        {
            public void next(Integer integer)
            {
                System.out.println(integer);
            }
            public void complete()
            {
                System.out.println("subscribe to StreamObservable complete success");
            }
            public void error(Throwable t)
            {
                System.out.println("subscribe to StreamObservable with error: \"" + t.getMessage() + "\"");//Not possible in this case!
            }
        });
        errorStreamObservable().subscribe(new StreamObserver<Integer>()
        {
            public void next(Integer integer)
            {
                System.out.println(integer);
            }
            public void complete()
            {
                System.out.println("subscribe to StreamObservable complete success");//Not possible in this case!
            }
            public void error(Throwable t)
            {
                System.out.println("subscribe to StreamObservable with error: \"" + t.getMessage() + "\"");
            }
        });
    }
    static private StreamObservable<Integer> successStreamObservable()
    {
        return new StreamObservable<Integer>()
        {
            public void subscribe(StreamObserver<Integer> o)
            {
                System.out.println("subscribe to StreamObservable in process...\nvalues:");
                o.next(1);
                o.next(2);
                o.next(3);
                o.next(4);
                o.next(5);
                o.next(6);
                o.next(7);
                o.next(8);
                o.next(9);
                o.complete();
            }
        };
    }
    static private StreamObservable<Integer> errorStreamObservable()
    {
        return new StreamObservable<Integer>()
        {
            public void subscribe(StreamObserver<Integer> o)
            {
                System.out.println("subscribe to StreamObservable in process...\nvalues:");
                o.next(1);
                o.next(2);
                o.next(3);
                o.next(4);
                o.next(5);
                o.next(6);
                o.error(new Exception("something wrong :("));
            }
        };
    }
}