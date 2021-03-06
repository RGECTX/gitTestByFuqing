
package com.greathack.homlin.daointerface.auxiliary;

import com.greathack.homlin.daointerface.system.IBaseDAO;
import com.greathack.homlin.pojo.auxiliary.AuxFjbzfp;
import com.greathack.homlin.pojo.auxiliary.AuxFjbzfpSearchCriteria;

import java.util.List;

public interface IAuxFjbzfpDAO extends IBaseDAO<AuxFjbzfp,Long> {
    List<AuxFjbzfp> search(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    long getSearchResultCount(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    List<AuxFjbzfp> findFjType1(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    List<AuxFjbzfp> findFjType2(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
    List<AuxFjbzfp> findFjType4(AuxFjbzfpSearchCriteria auxFjbzfpSearchCriteria);
   /* List<AuxFjbzfp> findFjType2(String fjType);
    List<AuxFjbzfp> findFjType4(String fjType);*/

    List<AuxFjbzfp> rytj();

    List<AuxFjbzfp> rytjcp();

    List<AuxFjbzfp> rytjcl();

    List<AuxFjbzfp> rytjcz();

    List<AuxFjbzfp> rytjfp();

    List<AuxFjbzfp> rytjfl();

    List<AuxFjbzfp> rytjfz();
}

