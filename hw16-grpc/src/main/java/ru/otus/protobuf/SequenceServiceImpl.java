package ru.otus.protobuf;

import io.grpc.stub.StreamObserver;

import ru.otus.protobuf.generated.GenerateSequenceMessage;
import ru.otus.protobuf.generated.SequenceMessage;
import ru.otus.protobuf.generated.SequenceServiceGrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;
import static ru.otus.protobuf.common.SleepUtils.sleep;
import static ru.otus.protobuf.common.GRPCAppConst.SERVER_DELAY;

public class SequenceServiceImpl extends SequenceServiceGrpc.SequenceServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(SequenceServiceImpl.class);

    @Override
    public void generateSequence(GenerateSequenceMessage request,
                                 StreamObserver<SequenceMessage> responseObserver) {
        log.info("Generate number from {} to {}", request.getFrom(), request.getTo());

        for (int i = request.getFrom(); i <= request.getTo(); i++) {
            var response = SequenceMessage.newBuilder()
                    .setNumber(i).build();

            responseObserver.onNext(response);
            log.info("Number has been sent: {}", i);
            sleep(TimeUnit.SECONDS,SERVER_DELAY);
        }
        responseObserver.onCompleted();
    }
}