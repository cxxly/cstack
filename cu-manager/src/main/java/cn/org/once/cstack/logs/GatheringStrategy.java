package cn.org.once.cstack.logs;

import cn.org.once.cstack.exception.ServiceException;

/**
 * Created by nicolas on 20/09/2016.
 */
public interface GatheringStrategy {
    public String gather(String container, String source, int maxRows) throws ServiceException;
}
