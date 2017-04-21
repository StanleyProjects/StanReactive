package stan.reactive.single;

import stan.reactive.Func;
import stan.reactive.Tuple;

public abstract class SingleObservable<T>
        implements ObservableSource<T>
{
    public <U> SingleObservable<U> map(final Func<T, U> f)
    {
        return new SingleObservable<U>()
        {
            public void subscribe(final SingleObserver<U> o)
            {
                SingleObservable.this.subscribe(new SingleObserver<T>()
                {
                    public void success(T t)
                    {
                        o.success(f.call(t));
                    }
                    public void error(Throwable t)
                    {
                        o.error(t);
                    }
                });
            }
        };
    }
    public <U> SingleObservable<U> flat(final Func<T, SingleObservable<U>> f)
    {
        return new SingleObservable<U>()
        {
            public void subscribe(final SingleObserver<U> o)
            {
                SingleObservable.this.subscribe(new SingleObserver<T>()
                {
                    public void success(T t)
                    {
                        f.call(t).subscribe(o);
                    }
                    public void error(Throwable t)
                    {
                        o.error(t);
                    }
                });
            }
        };
    }
    public <U> SingleObservable<U> chain(final SingleObserver<T> observer, final SingleObservable<U> observable)
    {
        return new SingleObservable<U>()
        {
            public void subscribe(final SingleObserver<U> o)
            {
                SingleObservable.this.subscribe(new SingleObserver<T>()
                {
                    public void success(T t)
                    {
                        observer.success(t);
                        observable.subscribe(o);
                    }
                    public void error(Throwable t)
                    {
                        observer.error(t);
                    }
                });
            }
        };
    }
    public <U> SingleObservable<Tuple<T, U>> merge(final SingleObservable<U> observable)
    {
        return SingleObservable.this.flat(new Func<T, SingleObservable<Tuple<T, U>>>()
        {
            public SingleObservable<Tuple<T, U>> call(final T t)
            {
                return observable.flat(new Func<U, SingleObservable<Tuple<T, U>>>()
                {
                    public SingleObservable<Tuple<T, U>> call(final U u)
                    {
                        return new SingleObservable<Tuple<T, U>>()
                        {
                            public void subscribe(SingleObserver<Tuple<T, U>> o)
                            {
                                o.success(new Tuple<T, U>()
                                {
                                    public T first()
                                    {
                                        return t;
                                    }
                                    public U second()
                                    {
                                        return u;
                                    }
                                });
                            }
                        };
                    }
                });
            }
        });
    }
}