package com.nk;

import com.nk.servlet.Connector.HttpConnector;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

public class Start {

    static Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) throws Exception {
        try (HttpConnector connector = new HttpConnector("0.0.0.0",8081)) {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
//        logger.info("jerrymouse http server was shutdown.");
    }
}