package stan.reactive.sample.single;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import stan.reactive.functions.Apply;
import stan.reactive.functions.Func;
import stan.reactive.Tuple;
import stan.reactive.functions.Worker;
import stan.reactive.single.SingleObservable;
import stan.reactive.single.SingleObserver;

public class SingleSample
{
    static public void sampleSingleObservable()
    {
        successObserveAnimals().subscribe(new SingleObserver<String>()
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
    static private SingleObservable<String> successObserveAnimals()
    {
        return SingleObservable.create(new Apply<String>()
        {
            public String apply()
            {
                System.out.println("subscribe to SingleObservable in process...");
                return "Cat Dog Penguin Platypus Elephant";
            }
        });
//        return new SingleObservable<String>()
//        {
//            public void subscribe(SingleObserver<String> o)
//            {
//                System.out.println("subscribe to SingleObservable in process...");
//                o.success("Cat Dog Penguin Platypus Elephant");
//            }
//        };
    }
    static private SingleObservable<String> successObservePlants()
    {
        return new SingleObservable<String>()
        {
            public void subscribe(SingleObserver<String> o)
            {
                System.out.println("subscribe to SingleObservable in process...");
                o.success("Oak Raspberries Bamboo Potatoes Corn Mango");
            }
        };
    }
    static private SingleObservable<String> errorSingleObservable()
    {
        return SingleObservable.create(new Worker<String>()
        {
            public String work()
                    throws Throwable
            {
                System.out.println("subscribe to SingleObservable in process...");
                throw new Exception("something wrong :(");
            }
        });
//        return new SingleObservable<String>()
//        {
//            public void subscribe(SingleObserver<String> o)
//            {
//                System.out.println("subscribe to SingleObservable in process...");
//                o.error(new Exception("something wrong :("));
//            }
//        };
    }

    static public void sampleSingleObservableMap()
    {
        map().subscribe(new SingleObserver.Just<List<String>>()
        {
            public void success(List<String> list)
            {
                System.out.println("animals: " + list);
            }
        });
    }
    static private SingleObservable<List<String>> map()
    {
        return successObserveAnimals().map(splitMap);
    }
    static private Func<String, List<String>> splitMap = new Func<String, List<String>>()
    {
        public List<String> call(String s)
        {
            System.out.println("map string to other string in process...");
            return Arrays.asList(s.split(" "));
        }
    };

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
        return map().flat(sizeMap);
    }
    static private Func<List<String>, SingleObservable<Integer>> sizeMap = new Func<List<String>, SingleObservable<Integer>>()
    {
        public SingleObservable<Integer> call(final List<String> list)
        {
            System.out.println("flat string to SingleObservable for emmit string length in process...");
            return new SingleObservable<Integer>()
            {
                public void subscribe(SingleObserver<Integer> o)
                {
                    o.success(list.size());
                }
            };
        }
    };

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
        return successObserveAnimals().map(splitMap).flat(sizeMap).chain(new SingleObserver.Just<Integer>()
        {
            public void success(Integer integer)
            {
                System.out.println("flat "+integer+" animals success, move on...");
            }
        }, successObservePlants().map(splitMap).flat(sizeMap)).chain(new SingleObserver.Just<Integer>()
        {
            public void success(Integer integer)
            {
                System.out.println("flat "+integer+" plants success now, move on...");
            }
        }, SingleObservable.create(new Apply<Long>()
        {
            public Long apply()
            {
                System.out.println("subscribe chain in process...");
                return System.nanoTime() - time;
            }
        }));
    }

    static public void sampleSingleObservableMerge()
    {
        mergeAnimalsWithPlants().subscribe(new SingleObserver.Just<Tuple<List<String>, List<String>>>()
        {
            public void success(Tuple<List<String>, List<String>> tuple)
            {
                System.out.println("merge animals with plants in process...");
                List<String> list = new ArrayList<String>(tuple.first());
                list.addAll(tuple.second());
                System.out.println("complete list: " + list);
            }
        });
    }
    static private SingleObservable<Tuple<List<String>, List<String>>> mergeAnimalsWithPlants()
    {
        return successObserveAnimals().map(splitMap).merge(successObservePlants().map(splitMap));
    }
}