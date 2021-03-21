package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxNoticeDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxNotice;
import com.greathack.homlin.pojo.auxiliary.AuxNoticeSearchCriteria;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxNoticeService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class AuxNoticeService implements IAuxNoticeService {

    private static Logger logger = LoggerFactory.getLogger(AuxNoticeService.class);
    private static IAuxNoticeDAO auxNoticeDAO = (IAuxNoticeDAO) DAOFactory.createDAO("IAuxNoticeDAO");

    @Override
    public AuxNotice add(AuxNotice auxNotice) throws ServiceException {
        try {
            auxNotice.setCreateTime(Common.getCurrentTime());
            auxNotice.setNoticeId(IdCreator.createId("auxNotice"));
            auxNoticeDAO.add(auxNotice);
            return auxNotice;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void update(AuxNotice auxNotice) {
        auxNoticeDAO.update(auxNotice);

    }

    @Override
    public void delete(Long noticeId) {
        auxNoticeDAO.delete(noticeId);
    }

    @Override
    public AuxNotice get(Long noticeId) {
        return auxNoticeDAO.findById(noticeId);
    }

    @Override
    public List<AuxNotice> findByExample(AuxNotice auxNotice) {
        return auxNoticeDAO.findByExample(auxNotice);
    }

    @Override
    public List<AuxNotice> findAll() {
        return auxNoticeDAO.findAll();
    }

    @Override
    public List<AuxNotice> search(AuxNoticeSearchCriteria criteria) {
        List<AuxNotice> noticeList = new ArrayList<AuxNotice>();
        if (criteria == null) {
            return noticeList;
        }
        return auxNoticeDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxNoticeSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        String key = Integer.toString(criteria.hashCode());
        String count = "0";
        Jedis jedis = null;
        try {
            jedis = SystemConfig.getJedisPool().getResource();
            if (jedis.exists(key)) {
                count = jedis.get(key);
                //重新设置超时
                jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
            } else {
                count = Long.toString(auxNoticeDAO.getSearchResultCount(criteria));
                jedis.set(key, count);
                //设置超时
                jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
            }
        } catch (Exception e) {
            logger.debug("redis有异常");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return Long.parseLong(count);
    }
}
