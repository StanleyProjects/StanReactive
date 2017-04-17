package stan.reactive.stream;

public interface StreamObserver<T>
{
    void next(T t);
    void complete();
    void error(Throwable t);
}