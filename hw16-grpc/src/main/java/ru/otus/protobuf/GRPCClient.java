package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.SequenceServiceGrpc;
import ru.otus.protobuf.generated.GenerateSequenceMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

import static ru.otus.protobuf.common.SleepUtils.sleep;
import static ru.otus.protobuf.common.GRPCAppConst.*;

public class GRPCClient {
    private static final Logger log = LoggerFactory.getLogger(GRPCClient.class);
    private static Long currentValue = 0L;

    public static void main(String[] args) throws InterruptedException {
        log.info("Creating connection to {} on port:{}", SERVER_HOST, SERVER_PORT);

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var remoteService = SequenceServiceGrpc.newStub(channel);
        log.info("Start client");
        new GRPCClient().runClientJob(remoteService);
        log.info("Shutdown client");

        channel.shutdown();
    }

    private void runClientJob(SequenceServiceGrpc.SequenceServiceStub remoteService) {
        var request = GenerateSequenceMessage.newBuilder()
                .setFrom(SERVER_VALUE_FROM).setTo(SERVER_VALUE_TO).build();
        var observer = new ClientStreamObserver();

        remoteService.generateSequence(request, observer);

        for (long i = CLIENT_VALUE_FROM; i <= CLIENT_VALUE_TO; i++) {
            currentValue++;
            calculateCurrentValue(observer);
            log.info("step: {} current Value: {}" ,i, currentValue);
            sleep(TimeUnit.SECONDS, CLIENT_DELAY);
        }
    }

    private long calculateCurrentValue(ClientStreamObserver observer) {
        var lastValue = observer.getLastAndReset();
        currentValue += lastValue;
        return currentValue;
    }
}
