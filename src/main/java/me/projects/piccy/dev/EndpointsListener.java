package me.projects.piccy.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@Profile({"dev"})
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger;

    EndpointsListener() {
        logger = LoggerFactory.getLogger(EndpointsListener.class);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((requestMappingInfo, handlerMethod) -> logger.info(
                        "{}{}",
                        handlerMethod.getShortLogMessage(),
                        requestMappingInfo.getDirectPaths()
                ));
    }
}