package stan.reactive.sample.notify;

import stan.reactive.functions.Action;
import stan.reactive.notify.NotifyObservable;
import stan.reactive.notify.NotifyObserver;

public class NotifySample
{
    static public void sampleNotifyObservable()
    {
        successNotifyObservable().subscribe(new NotifyObserver.Just()
        {
            public void notice()
            {
                System.out.println("subscribe to NotifyObservable success");
            }
        });
//        successNotifyObservable().subscribe(new NotifyObserver()
//        {
//            public void notice()
//            {
//                System.out.println("subscribe to NotifyObservable success");
//            }
//            public void error(Throwable t)
//            {
//                System.out.println("subscribe to NotifyObservable with error: \"" + t.getMessage() + "\"");//Not possible in this case!
//            }
//        });
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
        return NotifyObservable.create(new Runnable()
        {
            public void run()
            {
                System.out.println("subscribe to NotifyObservable in process...");
            }
        });
//        return new NotifyObservable()
//        {
//            public void subscribe(NotifyObserver o)
//            {
//                System.out.println("subscribe to NotifyObservable in process...");
//                o.notice();
//            }
//        };
    }
    static private NotifyObservable errorNotifyObservable()
    {
        return NotifyObservable.create(new Action()
        {
            public void run()
                    throws Throwable
            {
                System.out.println("subscribe to NotifyObservable in process...");
                throw new Exception("something wrong :(");
            }
        });
//        return new NotifyObservable()
//        {
//            public void subscribe(NotifyObserver o)
//            {
//                System.out.println("subscribe to NotifyObservable in process...");
//                o.error(new Exception("something wrong :("));
//            }
//        };
    }
}