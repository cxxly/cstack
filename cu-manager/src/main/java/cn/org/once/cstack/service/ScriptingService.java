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

package cn.org.once.cstack.service;

import java.util.List;

import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.model.Script;

public interface ScriptingService {

    public void execute(String scriptContent, String login, String password) throws ServiceException;

    public void save(Script script) throws ServiceException;

    public Script load(Integer id) throws ServiceException;

    public List<Script> loadAllScripts() throws ServiceException;

    public void delete(Script script) throws ServiceException;

}
