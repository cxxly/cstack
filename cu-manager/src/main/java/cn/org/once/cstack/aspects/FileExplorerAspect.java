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

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Message;
import cn.org.once.cstack.model.User;
import cn.org.once.cstack.service.MessageService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FileExplorerAspect extends CloudUnitAbstractAspect implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MessageService messageService;

	@AfterReturning("execution(* FileService.deleteFilesFromContainer(..))"
			+ " || execution(* FileService.sendFileToContainer(..))")
	public void afterReturningFileExplorer(JoinPoint joinPoint) throws ServiceException {
		Message message = new Message();
		User user = getAuthentificatedUser();
		message.setDate(new Date());
		message.setType(Message.INFO);
		message.setAuthor(user);
		message.setApplicationName((String) joinPoint.getArgs()[0]);

		switch (joinPoint.getSignature().getName().toUpperCase()) {
		case "DELETEFILESFROMCONTAINER":
			message.setEvent(user.getLogin() + " has removed this file : " + joinPoint.getArgs()[2]);
			break;
		}
		this.messageService.create(message);
	}

}
