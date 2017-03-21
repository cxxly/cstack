package cn.org.once.cstack.service;

import cn.org.once.cstack.dto.Command;
import cn.org.once.cstack.exception.ServiceException;

import java.util.List;

public interface CommandService {

    List<Command> listCommandByContainer(String applicationName, String containerName) throws ServiceException;

    String execCommand(Command command, String containerName, String applicationName) throws ServiceException;
}
