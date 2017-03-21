package cn.org.once.cstack.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import cn.org.once.cstack.config.events.ServerStartEvent;
import cn.org.once.cstack.dto.HttpOk;
import cn.org.once.cstack.dto.JsonResponse;
import cn.org.once.cstack.exception.CheckException;
import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Application;
import cn.org.once.cstack.model.EnvironmentVariable;
import cn.org.once.cstack.model.User;
import cn.org.once.cstack.service.ApplicationService;
import cn.org.once.cstack.service.EnvironmentService;
import cn.org.once.cstack.utils.AuthentificationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.org.once.cstack.config.events.ApplicationStartEvent;

@Controller
@RequestMapping("/application")
public class EnvironmentController implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);

    @Inject
    private AuthentificationUtils authentificationUtils;

    @Inject
    private EnvironmentService environmentService;

    @Inject
    private ApplicationService applicationService;

    @Inject
    private ApplicationEventPublisher applicationEventPublisher;

    @RequestMapping(value = "/{applicationName}/container/{containerName}/environmentVariables", method = RequestMethod.GET)
    public @ResponseBody List<EnvironmentVariable> loadAllEnvironmentVariables(@PathVariable String applicationName,
                                                                               @PathVariable String containerName) throws ServiceException, JsonProcessingException, CheckException {
        logger.info("Load");
        List<EnvironmentVariable> environmentVariableRequestList = environmentService
                .loadEnvironnmentsByContainer(containerName);
        return environmentVariableRequestList;
    }

    @RequestMapping(value = "/{applicationName}/container/{containerName}/environmentVariables/{id}", method = RequestMethod.GET)
    public @ResponseBody EnvironmentVariable loadEnvironmentVariable(@PathVariable String applicationName,
            @PathVariable String containerName, @PathVariable int id) throws ServiceException, CheckException {
        logger.info("Load");
        EnvironmentVariable environmentVariableRequest = environmentService.loadEnvironnment(id);
        return environmentVariableRequest;
    }

    @RequestMapping(value = "/{applicationName}/container/{containerName}/environmentVariables", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse addEnvironmentVariable(@PathVariable String applicationName,
                                        @PathVariable String containerName, @RequestBody EnvironmentVariable environmentVariableRequest)
            throws ServiceException, CheckException {
        User user = authentificationUtils.getAuthentificatedUser();
        Application application = applicationService.findByNameAndUser(user, applicationName);
        environmentService.save(user, environmentVariableRequest, applicationName, containerName);
        applicationEventPublisher.publishEvent(new ServerStartEvent(application.getServer()));
        applicationEventPublisher.publishEvent(new ApplicationStartEvent(application));
        return new HttpOk();

    }

    @RequestMapping(value = "/{applicationName}/container/{containerName}/environmentVariables/{id}", method = RequestMethod.PUT)
    public @ResponseBody JsonResponse updateEnvironmentVariable(@PathVariable String applicationName,
            @PathVariable String containerName, @PathVariable int id,
            @RequestBody EnvironmentVariable environmentVariableRequest) throws ServiceException, CheckException {
        User user = authentificationUtils.getAuthentificatedUser();
        environmentService.update(user, environmentVariableRequest, applicationName, containerName, id);
        return new HttpOk();
    }

    @RequestMapping(value = "/{applicationName}/container/{containerName}/environmentVariables/{id}", method = RequestMethod.DELETE)
    public @ResponseBody JsonResponse deleteEnvironmentVariable(@PathVariable String applicationName,
            @PathVariable String containerName, @PathVariable int id) throws ServiceException, CheckException {
        logger.info("Delete");
        User user = authentificationUtils.getAuthentificatedUser();
        environmentService.delete(user, id, applicationName, containerName);
        return new HttpOk();
    }
}
