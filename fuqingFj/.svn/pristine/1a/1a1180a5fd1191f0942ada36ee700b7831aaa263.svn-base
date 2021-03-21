package com.greathack.homlin.service.attachment;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.attachment.IAttachmentDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.attachment.Attachment;
import com.greathack.homlin.serviceinterface.attachment.IAttachmentService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AttachmentService implements IAttachmentService {

    private static Logger logger = LoggerFactory.getLogger(AttachmentService.class);
    private static IAttachmentDAO attachmentDAO = (IAttachmentDAO) DAOFactory.createDAO("IAttachmentDAO");
    @Override
    public Attachment add(Attachment instance) throws ServiceException {
        if(instance==null){
            return null;
        }
        //应用编码不能为空
        if(Validation.isEmpty(instance.getAppCode())){
            throw new ServiceException(120001,"INVALID_PARAMS");
        }
        //地址不能为空
        if(Validation.isEmpty(instance.getFilePath())){
            throw new ServiceException(120001,"INVALID_PARAMS");
        }
        instance.setCreateTime(Common.getCurrentTime());
        instance.setId(IdCreator.createId("Attachment"));
        attachmentDAO.add(instance);
        return instance;
    }

    @Override
    public void delete(Long id) {
        attachmentDAO.delete(id);
    }

    @Override
    public void update(Attachment instance) {
        attachmentDAO.update(instance);
    }

    @Override
    public Attachment get(Long id) {
        return attachmentDAO.findById(id);
    }

    @Override
    public List<Attachment> findByExample(Attachment instance) {
        return attachmentDAO.findByExample(instance);
    }
    @Override
    public Attachment findSingleAttachment(Attachment instance) {
        List<Attachment> attachmentList= attachmentDAO.findByExample(instance);
        if(attachmentList.size()>0){
            return attachmentList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void deleteByinstanceId(Long parseLong) {
        attachmentDAO.deleteByinstanceId(parseLong);
    }


    @Override
    public List<Attachment> findAll() {
        return attachmentDAO.findAll();
    }
}
