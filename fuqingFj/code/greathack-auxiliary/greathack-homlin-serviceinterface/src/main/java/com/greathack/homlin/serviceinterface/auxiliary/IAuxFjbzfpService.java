
package com.greathack.homlin.serviceinterface.auxiliary;

import com.greathack.homlin.pojo.auxiliary.AuxFjbzfp;
import com.greathack.homlin.pojo.auxiliary.AuxFjbzfpSearchCriteria;
import com.greathack.homlin.serviceinterface.system.IBaseService;

import java.util.List;


public interface IAuxFjbzfpService extends IBaseService<AuxFjbzfp,Long> {
    List<AuxFjbzfp> search(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    long getSearchResultCount(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    List<AuxFjbzfp> findFjType1(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    List<AuxFjbzfp> findFjType2(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    List<AuxFjbzfp> findFjType4(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    /*List<AuxFjbzfp> findFjType2(String fjType);
    List<AuxFjbzfp> findFjType4(String fjType);*/

}
