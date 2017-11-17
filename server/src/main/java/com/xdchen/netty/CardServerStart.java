package com.xdchen.netty;

import com.xdchen.netty.server.CardServer;
import com.xdchen.netty.server.CardServerInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @project:		netty-learn
 * @Title:		CardServer.java
 * @Package:
 @author: 		xdchen
 * @date:		2017-11-15
 * @description:
 * @version:
 */
public class CardServerStart {
    private static int port;
    public static ApplicationContext factory;

    public static void main(String[] args) throws Exception {
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        else {
            port = 8080;
        }
        run();
    }
    private static void run()
            throws Exception
    {
        factory = new ClassPathXmlApplicationContext("spring*.xml");
        CardServerInitializer initializer = factory.getBean(CardServerInitializer.class);

        CardServer server = new CardServer(port);
        server.setInitializer(initializer);
        server.run();
        System.out.println("server is running……");
    }
}
