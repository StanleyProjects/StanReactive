package stan.reactive.sample.single;

import stan.reactive.Func;
import stan.reactive.Tuple;
import stan.reactive.single.SingleObservable;
import stan.reactive.single.SingleObserver;

public class SingleSample
{
    static public void sampleSingleObservable()
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

    static public void sampleSingleObservableMap()
    {
        map().subscribe(new SingleObserver.Just<String>()
        {
            public void success(String s)
            {
                System.out.println(s);
            }
        });
    }
    static private SingleObservable<String> map()
    {
        return successSingleObservable().map(new Func<String, String>()
        {
            public String call(String s)
            {
                System.out.println("map string to other string in process...");
                return "successSingleObservable emmit string: \"" + s + "\"";
            }
        });
    }

    static public void sampleSingleObservableFlat()
    {
        flat().subscribe(new SingleObserver.Just<Integer>()
        {
            public void success(Integer integer)
            {
                System.out.println("chars count = " + integer);
            }
        });
    }
    static private SingleObservable<Integer> flat()
    {
        return map().flat(new Func<String, SingleObservable<Integer>>()
        {
            public SingleObservable<Integer> call(final String s)
            {
                System.out.println("flat string to SingleObservable for emmit string length in process...");
                return new SingleObservable<Integer>()
                {
                    public void subscribe(SingleObserver<Integer> o)
                    {
                        o.success(s.length());
                    }
                };
            }
        });
    }

    static public void sampleSingleObservableChain()
    {
        chain().subscribe(new SingleObserver.Just<Long>()
        {
            public void success(Long l)
            {
                System.out.println("time spent = " + l);
            }
        });
    }
    static private SingleObservable<Long> chain()
    {
        final long time = System.nanoTime();
        return flat().chain(new SingleObserver.Just<Integer>()
        {
            public void success(Integer integer)
            {
                System.out.println("flat success, move on...");
            }
        }, flat()).chain(new SingleObserver.Just<Integer>()
        {
            public void success(Integer integer)
            {
                System.out.println("flat success again, move on...");
            }
        }, new SingleObservable<Long>()
        {
            public void subscribe(SingleObserver<Long> o)
            {
                System.out.println("subscribe chain in process...");
                o.success(System.nanoTime() - time);
            }
        });
    }

    static public void sampleSingleObservableMerge()
    {
        merge().subscribe(new SingleObserver.Just<Tuple<Integer, Long>>()
        {
            public void success(Tuple<Integer, Long> tuple)
            {
                System.out.println("chars count = " + tuple.first() + " and time spent = " + tuple.second());
            }
        });
    }
    static private SingleObservable<Tuple<Integer, Long>> merge()
    {
        return flat().merge(chain());
    }
}