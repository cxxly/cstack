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

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.org.once.cstack.dto.FileUnit;
import cn.org.once.cstack.dto.SourceUnit;
import cn.org.once.cstack.exception.CheckException;
import cn.org.once.cstack.exception.ServiceException;

/**
 * Created by nicolas on 20/05/15.
 */
public interface FileService {

    List<FileUnit> listByContainerIdAndPath(String containerId, String path)
        throws ServiceException;

    List<SourceUnit> listLogsFilesByContainer(String containerId)
        throws ServiceException;

    void sendFileToContainer(String containerId, String destination, MultipartFile fileUpload, String contentFileName, String contentFileData)
            throws ServiceException, CheckException;

    int getFileFromContainer(String containerId,
                              String pathFile, OutputStream outputStream)
        throws ServiceException;

    void deleteFilesFromContainer(String applicationName, String containerId, String path)
        throws ServiceException;

    void createDirectory(String applicationName, String containerId, String path)
            throws ServiceException;

    String tailFile(String containerId, String filename, Integer maxRows)
            throws ServiceException;

    String getLogDirectory(String containerId)
            throws ServiceException;
}
