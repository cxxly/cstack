package cn.org.once.cstack.logs;

import javax.inject.Inject;

import cn.org.once.cstack.exception.ServiceException;
import cn.org.once.cstack.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by nicolas on 20/09/2016.
 */
@Component("tail")
public class TailStrategy implements GatheringStrategy {

    private Logger logger = LoggerFactory.getLogger(TailStrategy.class);

    @Inject
    private FileService fileService;

    @Override
    public String gather(String container, String source, int maxRows) throws ServiceException {
        String logs = "";
        try {
            logs = fileService.tailFile(container, source, maxRows);
        } catch (Exception e) {
            logger.error(container + "," + source, e);
        }
        return logs;
    }
}
