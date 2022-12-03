package com.tian.mapper;

import com.tian.entity.GoodsInfo;
import com.tian.vo.GoodsListVO;
import com.tian.vo.SearchGoodsListVO;
import com.tian.vo.StockNumDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Long goodsId);

    GoodsInfo selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKeyWithBLOBs(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    List<GoodsInfo> findNewBeeMallGoodsList(GoodsListVO goodsListVO);

    int getTotalNewBeeMallGoods(GoodsListVO goodsListVO);

    List<GoodsInfo> selectByPrimaryKeys(List<Long> goodsIds);

    List<GoodsInfo> findNewBeeMallGoodsListBySearch(SearchGoodsListVO searchGoodsListVO);

    int getTotalNewBeeMallGoodsBySearch(SearchGoodsListVO searchGoodsListVO);

    int batchInsert(@Param("newBeeMallGoodsList") List<GoodsInfo> goodsInfoList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds") Long[] orderIds, @Param("sellStatus") int sellStatus);

    int selectCount();

}