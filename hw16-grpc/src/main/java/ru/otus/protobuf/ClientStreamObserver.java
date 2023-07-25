package ru.otus.protobuf;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.SequenceMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static ru.otus.protobuf.common.SleepUtils.sleep;


public class ClientStreamObserver implements StreamObserver<SequenceMessage> {
    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);
    private long value = 0;

    @Override
    public void onNext(SequenceMessage valueFromServer) {
        log.info("Got value from server: {}", setValue(valueFromServer.getNumber()));
    }

    @Override
    public void onError(Throwable e) {
        log.error("Catch error: {}", e.toString());
    }

    @Override
    public void onCompleted() {
        log.info("Completed");
    }

    public synchronized long getLastAndReset() {
        long returnedVal = this.value;
        this.value = 0;
        return returnedVal;
    }

    private synchronized long setValue(long newValue){
        this.value = newValue;
        return value;
    }
}
