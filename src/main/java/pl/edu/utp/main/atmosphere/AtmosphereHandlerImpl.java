package pl.edu.utp.main.atmosphere;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.Heartbeat;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.atmosphere.cpr.ApplicationConfig.MAX_INACTIVE;

@ManagedService(path = "/auction-website", atmosphereConfig = MAX_INACTIVE + "=120000")
public class AtmosphereHandlerImpl {

    private final Logger logger = LoggerFactory.getLogger(AtmosphereHandlerImpl.class);

//    @Inject
//    private BroadcasterFactory factory;
//
//    @Inject
//    @Named("/chat")
//    private Broadcaster broadcaster;

    @Heartbeat
    public void onHeartbeat(final AtmosphereResourceEvent event) {
        logger.trace("Heartbeat send by {}", event.getResource());
    }

    @Ready
    public void onReady(AtmosphereResource r ) {
        logger.info("Browser {} connected", r.uuid());
//        logger.info("BroadcasterFactory used {}", factory.getClass().getName());
//        logger.info("Broadcaster injected {}", broadcaster.getID());
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            logger.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }

    @org.atmosphere.config.service.Message()
    public String onMessage(String message) throws IOException {
        logger.info("Method onMessage in atmosphereHandler");
        return message;
    }
}
