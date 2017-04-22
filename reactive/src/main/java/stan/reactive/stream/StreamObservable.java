package stan.reactive.stream;

import stan.reactive.Func;

public abstract class StreamObservable<T>
        implements ObservableSource<T>
{
    public <U> StreamObservable<U> map(final Func<T, U> f)
    {
        return new StreamObservable<U>()
        {
            public void subscribe(final StreamObserver<U> o)
            {
                StreamObservable.this.subscribe(new StreamObserver<T>()
                {
                    public void next(T t)
                    {
                        o.next(f.call(t));
                    }
                    public void complete()
                    {
                        o.complete();
                    }
                    public void error(Throwable t)
                    {
                        o.error(t);
                    }
                });
            }
        };
    }
    public StreamObservable<T> filter(final Func<T, Boolean> f)
    {
        return new StreamObservable<T>()
        {
            public void subscribe(final StreamObserver<T> o)
            {
                StreamObservable.this.subscribe(new StreamObserver<T>()
                {
                    public void next(T t)
                    {
                        if(f.call(t))
                            o.next(t);
                    }
                    public void complete()
                    {
                        o.complete();
                    }
                    public void error(Throwable t)
                    {
                        o.error(t);
                    }
                });
            }
        };
    }
}