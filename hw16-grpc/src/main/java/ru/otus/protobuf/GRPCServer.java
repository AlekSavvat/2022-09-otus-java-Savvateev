package ru.otus.protobuf;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import static ru.otus.protobuf.common.GRPCAppConst.SERVER_PORT;

public class GRPCServer {
    private static final Logger log = LoggerFactory.getLogger(GRPCServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {

       var server = ServerBuilder.forPort(SERVER_PORT)
                .addService(new SequenceServiceImpl())
                .build();

        server.start();
        log.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
