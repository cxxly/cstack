package cn.org.once.cstack.service;

import cn.org.once.cstack.exception.ServiceException;

public interface VolumeAssociationService {
	Integer checkVolumeAssociationPathAlreadyPresent(String path, int id) throws ServiceException;
}
