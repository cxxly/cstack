package cn.org.once.cstack.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.once.cstack.dto.Command;
import cn.org.once.cstack.dto.HttpOk;
import cn.org.once.cstack.dto.JsonResponse;
import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Application;
import cn.org.once.cstack.model.Status;
import cn.org.once.cstack.model.User;
import cn.org.once.cstack.service.ApplicationService;
import cn.org.once.cstack.service.CommandService;
import cn.org.once.cstack.utils.AuthentificationUtils;

@Controller
@RequestMapping("/application")
public class CommandController {

    private final Logger logger = LoggerFactory.getLogger(CommandController.class);

    @Inject
    private CommandService commandService;

    @Inject
    private ApplicationService applicationService;

    @Inject
    private AuthentificationUtils authentificationUtils;

    @RequestMapping(value = "/{applicationName}/container/{containerName}/command", method = RequestMethod.GET)
    public @ResponseBody List<Command> listCommandByImage(@PathVariable String applicationName,
                                                           @PathVariable String containerName) throws ServiceException {
        logger.info("Load by container");
        List<Command> commands = commandService.listCommandByContainer(applicationName, containerName);
        return commands;
    }

    @RequestMapping(value = "/{applicationName}/container/{containerName}/command/{filename}/exec", method = RequestMethod.POST,
        consumes = "application/json")
    public @ResponseBody JsonResponse execCommand(@PathVariable String applicationName, @PathVariable String containerName,
                                                  @PathVariable String filename, @RequestBody Command command) throws ServiceException {
        logger.info("Execute by filename");
        User user = authentificationUtils.getAuthentificatedUser();
        Application application = applicationService.findByNameAndUser(user, applicationName);
        try {
            applicationService.setStatus(application, Status.PENDING);
            String output = commandService.execCommand(command, containerName, applicationName);
            logger.debug(output);
        } finally {
            applicationService.setStatus(application, Status.START);
        }
        return new HttpOk();
    }
}
