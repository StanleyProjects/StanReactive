package stan.reactive.single;

public interface SingleObserver<T>
{
    void success(T t);
    void error(Throwable t);
}