# StanReactive
reactive realization observer pattern on java

<img src="media/icon.png" width="128" height="128" />

# Pretty, simple, powerful

This project is based on:
- **NotifyObservable** - notify when event is triggered (not emit data)
- **SingleObservable** - emit only one object if work after the subscription was successful
- **StreamObservable** - emit stream data and notify about complete if work after the subscription was successful

All observables notify about error (with *Throwable*) if work after the subscription was not successful