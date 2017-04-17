package stan.reactive.notify;

public interface NotifyObserver
{
    void notice();
    void error(Throwable t);
}