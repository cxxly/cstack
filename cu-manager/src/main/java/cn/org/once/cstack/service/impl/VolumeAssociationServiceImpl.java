package cn.org.once.cstack.service.impl;


import javax.inject.Inject;

import cn.org.once.cstack.service.VolumeAssociationService;
import org.springframework.stereotype.Service;

import cn.org.once.cstack.dao.VolumeAssociationDAO;
import cn.org.once.cstack.exception.ServiceException;


@Service
public class VolumeAssociationServiceImpl implements VolumeAssociationService {

@Inject
private VolumeAssociationDAO volumeAssociationDAO;


	@Override
	public Integer checkVolumeAssociationPathAlreadyPresent(String path, int id) throws ServiceException {
		return volumeAssociationDAO.countVolumeAssociationByPathAndServer(path, id);
	}

}
