package com.greathack.homlin.serviceinterface.attachment;

import com.greathack.homlin.pojo.attachment.Attachment;
import com.greathack.homlin.serviceinterface.system.IBaseService;


public interface IAttachmentService extends IBaseService<Attachment, Long> {
    public Attachment findSingleAttachment(Attachment attachment);
    
    void deleteByinstanceId(Long parseLong);
}
