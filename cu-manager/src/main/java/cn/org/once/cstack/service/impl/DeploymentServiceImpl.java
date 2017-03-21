/*
 * LICENCE : CloudUnit is available under the GNU Affero General Public License : https://gnu.org/licenses/agpl.html
 * but CloudUnit is licensed too under a standard commercial license.
 * Please contact our sales team if you would like to discuss the specifics of our Enterprise license.
 * If you are not sure whether the AGPL is right for you,
 * you can always test our software under the AGPL and inspect the source code before you contact us
 * about purchasing a commercial license.
 *
 * LEGAL TERMS : "CloudUnit" is a registered trademark of Treeptik and can't be used to endorse
 * or promote products derived from this project without prior written permission from Treeptik.
 * Products or services derived from this software may not be called "CloudUnit"
 * nor may "Treeptik" or similar confusing terms appear in their names without prior written permission.
 * For any questions, contact us : contact@treeptik.fr
 */


package cn.org.once.cstack.service.impl;

import cn.org.once.cstack.dao.DeploymentDAO;
import cn.org.once.cstack.exception.CheckException;
import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Application;
import cn.org.once.cstack.model.Deployment;
import cn.org.once.cstack.model.DeploymentType;
import cn.org.once.cstack.service.ApplicationService;
import cn.org.once.cstack.service.DeploymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

@Service
public class DeploymentServiceImpl
        implements DeploymentService {

    @Inject
    private DeploymentDAO deploymentDAO;

    @Inject
    private ApplicationService applicationService;

    @Override
    @Transactional
    public Deployment create(Application application, DeploymentType deploymentType, String contextPath)
            throws ServiceException, CheckException {
        try {
            Deployment deployment = new Deployment();
            deployment.setApplication(application);
            deployment.setType(deploymentType);
            deployment.setDate(new Date());
            application = applicationService.findByNameAndUser(application
                    .getUser(), application.getName());
            application.setDeploymentStatus(Application.ALREADY_DEPLOYED);
            application.setContextPath(contextPath);
            applicationService.saveInDB(application);
            return deploymentDAO.save(deployment);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public Deployment find(Deployment deployment)
            throws ServiceException {
        try {
            return deploymentDAO.findOne(deployment.getId());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public List<Deployment> findByApp(Application application)
            throws ServiceException {
        try {
            return deploymentDAO.findAllByApplication(application);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getLocalizedMessage()
                    + application.getName(), e);
        }
    }
}
