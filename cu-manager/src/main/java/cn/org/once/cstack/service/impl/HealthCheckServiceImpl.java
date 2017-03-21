package cn.org.once.cstack.service.impl;

import cn.org.once.cstack.utils.ContainerUtils;
import cn.org.once.cstack.config.events.UnexpectedContainerStatusEvent;
import cn.org.once.cstack.docker.core.DockerCloudUnitClient;
import cn.org.once.cstack.docker.model.State;
import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Application;
import cn.org.once.cstack.model.Server;
import cn.org.once.cstack.model.Status;
import cn.org.once.cstack.service.ApplicationService;
import cn.org.once.cstack.service.HealthCheckService;
import cn.org.once.cstack.service.ModuleService;
import cn.org.once.cstack.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by guillaume on 05/10/16.
 */
@Component
public class HealthCheckServiceImpl implements HealthCheckService {

    private Logger logger = LoggerFactory.getLogger(HealthCheckService.class);

    @Inject
    private ApplicationService applicationService;

    @Inject
    private ServerService serverService;

    @Inject
    private DockerCloudUnitClient dockerCloudUnitClient;

    @Inject
    private ModuleService moduleService;

    @Inject
    private ApplicationEventPublisher publisher;

    @Override
    public void checkAndRebootApplications(){
        try {
            List<Application> applications = applicationService.findAll();
            applications.stream().filter(a-> a.getStatus().equals(Status.START)).forEach(a->{
                checkNotStartedModules(a);
                checkNotStartedServer(a);
            });
            applications.stream().filter(a-> a.getStatus().equals(Status.STOP)).forEach(a->{
                checkNotStoppedModules(a);
                checkNotStoppedServer(a);
            });

        } catch (ServiceException e) {
            logger.error("An error occures when check and reboot started apps : "
                    + e.getLocalizedMessage());
            e.printStackTrace();
        }


    }

    private void checkNotStartedModules(Application a) {
        a.getModules().stream().forEach(m-> {
            final State moduleState = dockerCloudUnitClient.findContainer(
                    ContainerUtils.newStartInstance(m.getName(), null, null, false))
                    .getState();
            if(!moduleState.getRunning()){
                logger.warn("Module container is not started... Trying to restart it." );
                publisher.publishEvent(
                        new UnexpectedContainerStatusEvent(String.format("This module is stopped but should be started : %s", m.getName())));
                try {
                    moduleService.startModule(m.getName());
                } catch (ServiceException e) {
                    logger.error("An error occures when check and reboot started apps : "
                            + e.getLocalizedMessage());
                }
            }
        });
    }
    private void checkNotStoppedModules(Application a) {
        a.getModules().stream().forEach(m-> {
            final State moduleState = dockerCloudUnitClient.findContainer(
                    ContainerUtils.newStartInstance(m.getName(), null, null, false))
                    .getState();
            if(moduleState.getRunning()){
                logger.warn("Module container is not stopped... Trying to stop it." );
                publisher.publishEvent(
                        new UnexpectedContainerStatusEvent(String.format("This module is started but should be stopped : %s", m.getName())));
                try {
                    moduleService.stopModule(m.getName());
                } catch (ServiceException e) {
                    logger.error("An error occures when check and reboot shutdown apps : "
                            + e.getLocalizedMessage());
                }
            }
        });
    }

    private void checkNotStartedServer(Application a) {
        final State serverState = dockerCloudUnitClient.findContainer(
                ContainerUtils.newStartInstance(a.getServer().getName(), null, null, false))
                .getState();
        if(!serverState.getRunning()){
           logger.warn("Server container is not started... Trying to restart it." );
            publisher.publishEvent(
                    new UnexpectedContainerStatusEvent(String.format("This server is stopped but should be started : %s", a.getServer().getName())));
           try {
               Server server = a.getServer();
               server.setApplication(a);
               serverService.startServer(server);
           } catch (ServiceException e) {
               logger.error("An error occures when check and reboot started apps : "
                       + e.getLocalizedMessage());
           }
       }
    }

    private void checkNotStoppedServer(Application a) {
        final State serverState = dockerCloudUnitClient.findContainer(
                ContainerUtils.newStartInstance(a.getServer().getName(), null, null, false))
                .getState();

        if(serverState.getRunning()){
            logger.warn("Server container is not stopped... Trying to stop it." );
            publisher.publishEvent(
                    new UnexpectedContainerStatusEvent(String.format("This server is started but should be stopped : %s", a.getServer().getName())));
            try {
                serverService.stopServer(a.getServer());
            } catch (ServiceException e) {
                logger.error("An error occures when check and shutdown apps : "
                        + e.getLocalizedMessage());
            }
        }
    }


}
