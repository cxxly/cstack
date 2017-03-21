package cn.org.once.cstack.service;

import java.util.List;

import cn.org.once.cstack.exception.CheckException;
import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Application;
import cn.org.once.cstack.model.EnvironmentVariable;
import cn.org.once.cstack.model.User;

public interface EnvironmentService {

    EnvironmentVariable update(User user, EnvironmentVariable environmentVariable, String applicationName,
            String containerName, Integer id) throws ServiceException;

    EnvironmentVariable loadEnvironnment(int id) throws ServiceException, CheckException;

    List<EnvironmentVariable> loadEnvironnmentsByContainer(String containerName) throws ServiceException;

    void delete(User user, int id, String applicationName, String containerName) throws ServiceException;

    void save(User user, List<EnvironmentVariable> environments, String applicationName, String containerName)
            throws ServiceException;

    EnvironmentVariable save(User user, EnvironmentVariable environment, String applicationName, String containerName)
            throws ServiceException;

    void createInDatabase(List<EnvironmentVariable> environments, String containerName, Application application);

    void delete(User user, List<EnvironmentVariable> envs, String applicationName, String containerName)
            throws ServiceException;

}
