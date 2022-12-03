package com.tian.mapper;

import com.tian.entity.IndexConfig;
import com.tian.vo.ConfigsListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndexConfigMapper {
    int deleteByPrimaryKey(Long configId);

    int insert(IndexConfig record);

    int insertSelective(IndexConfig record);

    IndexConfig selectByPrimaryKey(Long configId);

    IndexConfig selectByTypeAndGoodsId(@Param("configType") int configType, @Param("goodsId") Long goodsId);

    int updateByPrimaryKeySelective(IndexConfig record);

    int updateByPrimaryKey(IndexConfig record);

    List<IndexConfig> findIndexConfigList(ConfigsListVO configsListVO);

    int getTotalIndexConfigs(ConfigsListVO configsListVO);

    int deleteBatch(Long[] ids);

    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
}