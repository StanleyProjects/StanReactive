package stan.reactive.sample.stream;

import java.util.Arrays;
import java.util.List;

import stan.reactive.functions.Apply;
import stan.reactive.functions.Func;
import stan.reactive.stream.StreamObservable;
import stan.reactive.stream.StreamObserver;

public class StreamSample
{
    static public void sampleStreamObservable()
    {
        successObserveAnimals().subscribe(new StreamObserver.Just<String>()
        {
            public void next(String s)
            {
                System.out.println(s);
            }
            public void complete()
            {
                System.out.println("subscribe to StreamObservable complete success");
            }
        });
//        successObserveAnimals().subscribe(new StreamObserver<String>()
//        {
//            public void next(String s)
//            {
//                System.out.println(s);
//            }
//            public void complete()
//            {
//                System.out.println("subscribe to StreamObservable complete success");
//            }
//            public void error(Throwable t)
//            {
//                System.out.println("subscribe to StreamObservable with error: \"" + t.getMessage() + "\"");//Not possible in this case!
//            }
//        });
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
    static private StreamObservable<String> successObserveAnimals()
    {
//        return StreamObservable.create("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab");
        return StreamObservable.create(new Apply<List<String>>()
        {
            public List<String> apply()
            {
                System.out.println("subscribe to StreamObservable in process...");
                return Arrays.asList("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab");
            }
        });
//        return new StreamObservable<String>()
//        {
//            public void subscribe(StreamObserver<String> o)
//            {
//                System.out.println("subscribe to StreamObservable in process...");
//                o.next("Cat");
//                o.next("Dog");
//                o.next("Penguin");
//                o.next("Platypus");
//                o.next("Elephant");
//                o.next("Camel");
//                o.next("Goat");
//                o.next("Lion");
//                o.next("Turtle");
//                o.next("Crab");
//                o.complete();
//            }
//        };
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

    static public void sampleStreamObservableMap()
    {
        System.out.print("map animals in process...\n");
        successObserveAnimals().map(lengthMap).subscribe(new StreamObserver<Integer>()
        {
            public void next(Integer integer)
            {
                System.out.print(integer + " ");
            }
            public void complete()
            {
                System.out.print("\nmap animals complete success");
            }
            public void error(Throwable t)
            {
                //Not possible in this case!
            }
        });
    }
    static private Func<String, Integer> lengthMap = new Func<String, Integer>()
    {
        public Integer call(String s)
        {
            return s.length();
        }
    };

    static public void sampleStreamObservableFilter()
    {
        System.out.print("filter animals in process...\n");
        successObserveAnimals().filter(stringFilter).subscribe(new StreamObserver<String>()
        {
            public void next(String s)
            {
                System.out.print(s + " ");
            }
            public void complete()
            {
                System.out.print("\nfilter animals complete success");
            }
            public void error(Throwable t)
            {
                //Not possible in this case!
            }
        });
    }
    static private Func<String, Boolean> stringFilter = new Func<String, Boolean>()
    {
        public Boolean call(String s)
        {
            //all strings who begins with 'c' character
            return s.length() != 0 && String.valueOf(s.charAt(0))
                                            .toLowerCase()
                                            .compareTo("c") == 0;
        }
    };
}