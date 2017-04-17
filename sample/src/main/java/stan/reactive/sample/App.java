package stan.reactive.sample;

import stan.reactive.notify.NotifyObservable;
import stan.reactive.notify.NotifyObserver;
import stan.reactive.single.SingleObservable;
import stan.reactive.single.SingleObserver;
import stan.reactive.stream.StreamObservable;
import stan.reactive.stream.StreamObserver;

public class App
{
    static public void main(String[] args)
    {
        System.out.println("\n\tSample NotifyObservable");
        sampleNotifyObservable();
        System.out.println("\n\tSample SingleObservable");
        sampleSingleObservable();
        System.out.println("\n\tSample StreamObservable");
        sampleStreamObservable();
    }

    static private void sampleNotifyObservable()
    {
        successNotifyObservable().subscribe(new NotifyObserver()
        {
            public void notice()
            {
                System.out.println("subscribe to NotifyObservable success");
            }
            public void error(Throwable t)
            {
                System.out.println("subscribe to NotifyObservable with error: \"" + t.getMessage() + "\"");//Not possible in this case!
            }
        });
        errorNotifyObservable().subscribe(new NotifyObserver()
        {
            public void notice()
            {
                System.out.println("subscribe to NotifyObservable success");//Not possible in this case!
            }
            public void error(Throwable t)
            {
                System.out.println("subscribe to NotifyObservable with error: \"" + t.getMessage() + "\"");
            }
        });
    }
    static private NotifyObservable successNotifyObservable()
    {
        return new NotifyObservable()
        {
            public void subscribe(NotifyObserver o)
            {
                System.out.println("subscribe to NotifyObservable in process...");
                o.notice();
            }
        };
    }
    static private NotifyObservable errorNotifyObservable()
    {
        return new NotifyObservable()
        {
            public void subscribe(NotifyObserver o)
            {
                System.out.println("subscribe to NotifyObservable in process...");
                o.error(new Exception("something wrong :("));
            }
        };
    }

    static private void sampleSingleObservable()
    {
        successSingleObservable().subscribe(new SingleObserver<String>()
        {
            public void success(String s)
            {
                System.out.println("subscribe to SingleObservable success: \"" + s + "\"");
            }
            public void error(Throwable t)
            {
                System.out.println("subscribe to SingleObservable with error: \"" + t.getMessage() + "\"");//Not possible in this case!
            }
        });
        errorSingleObservable().subscribe(new SingleObserver<String>()
        {
            public void success(String s)
            {
                System.out.println("subscribe to SingleObservable success: \"" + s + "\"");//Not possible in this case!
            }
            public void error(Throwable t)
            {
                System.out.println("subscribe to SingleObservable with error: \"" + t.getMessage() + "\"");
            }
        });
    }
    static private SingleObservable<String> successSingleObservable()
    {
        return new SingleObservable<String>()
        {
            public void subscribe(SingleObserver<String> o)
            {
                System.out.println("subscribe to SingleObservable in process...");
                o.success("hi from SingleObservable :)");
            }
        };
    }
    static private SingleObservable<String> errorSingleObservable()
    {
        return new SingleObservable<String>()
        {
            public void subscribe(SingleObserver<String> o)
            {
                System.out.println("subscribe to SingleObservable in process...");
                o.error(new Exception("something wrong :("));
            }
        };
    }

    static private void sampleStreamObservable()
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