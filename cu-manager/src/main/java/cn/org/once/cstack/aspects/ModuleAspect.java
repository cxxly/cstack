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

package cn.org.once.cstack.aspects;

import cn.org.once.cstack.model.Message;
import cn.org.once.cstack.exception.MonitorException;
import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Application;
import cn.org.once.cstack.model.Module;
import cn.org.once.cstack.model.User;
import cn.org.once.cstack.service.MessageService;
import cn.org.once.cstack.utils.MessageUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;

@Aspect
@Component
public class ModuleAspect
    extends CloudUnitAbstractAspect
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String initModule = "INITMODULE";

    private final String createType = "CREATE";

    private final String deleteType = "REMOVE";

    private final Logger logger = LoggerFactory.getLogger(ModuleAspect.class);

    @Inject
    private MessageService messageService;

    @Before("execution(* ModuleService.remove(..)) " +
        "|| execution(* ModuleService.initModule(..))")
    public void beforeModule(JoinPoint joinPoint)
        throws MonitorException, ServiceException {

        User user = getAuthentificatedUser();
        Message message = null;

        Module module = null;
        switch (joinPoint.getSignature().getName().toUpperCase()) {

            case initModule:
                Application application = (Application) joinPoint.getArgs()[0];
                module = (Module) joinPoint.getArgs()[1];
                message = MessageUtils.writeBeforeModuleMessage(user,
                    module.getName(), application.getDisplayName(),
                    createType);
                logger.info(message.toString());
                messageService.create(message);
                break;

        }

    }

    @AfterReturning(pointcut = "execution(* ModuleService.remove(..)) " +
        "&& execution(* ModuleService.initModule(..))",
        returning = "result")
    public void afterReturningModule(StaticPart staticPart, Object result)
        throws MonitorException {
        try {
            if (result == null)
                return;
            Module module = (Module) result;
            User user = module.getApplication().getUser();
            // scape tool module
            if (!module.getImage().getImageType().equalsIgnoreCase("tool")) {
                Message message = null;
                switch (staticPart.getSignature().getName().toUpperCase()) {
                    case initModule:
                        message = MessageUtils.writeModuleMessage(user, module,
                            createType);
                        break;

                    case deleteType:
                        message = MessageUtils.writeModuleMessage(user, module,
                            deleteType);
                        break;
                }
                if (message != null) {
                    logger.info(message.toString());
                    messageService.create(message);
                }
            }
        } catch (ServiceException e) {
            throw new MonitorException("Error afterReturningApplication", e);

        }
    }

    @AfterThrowing(pointcut = "execution(* ModuleService.remove(..)) " +
        "|| execution(* ModuleService.initModule(..))",
        throwing = "e")
    public void afterThrowingModule(StaticPart staticPart,
                                    Exception e)
        throws ServiceException {
        User user = this.getAuthentificatedUser();
        Message message = null;
        logger.debug("CALLED CLASS : " + staticPart.getSignature().getName());
        switch (staticPart.getSignature().getName().toUpperCase()) {
            case initModule:
                message = MessageUtils.writeAfterThrowingModuleMessage(e, user,
                    initModule);
                break;
            case deleteType:
                message = MessageUtils.writeAfterThrowingModuleMessage(e, user,
                    deleteType);
                break;
        }
        if (message != null) {
            messageService.create(message);
        }
    }

}
