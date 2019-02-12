package com.jcj.sparrow.service;

import com.alibaba.fastjson.JSON;
import com.jcj.sparrow.domain.MeasureData;
import com.jcj.sparrow.repository.MeasureDataRepo;
import com.jcj.sparrow.systemaop.SystemAnnotationLog;
import com.jcj.sparrow.utils.FilterPureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: 江成军
 * @Date: 2019/01/30 15:08
 * @Description: 测量信息业务层，对应MongoDB,
 *               (混合利用MongoRepository和MongoTemplate，前者便利，后者灵活，简单固定条件的查询用MongoRepository，可变动态复杂查询用MongoTemplate)
 */
@Service
public class MeasureDataService
{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MeasureDataRepo measureDataRepo;

    public Page<MeasureData> basicPageList(Pageable pageable)
    {
        /**
        *@Author 江成军
        *@Description 基本的查询分页,没有查询条件
        *@Date 2019/02/09 16:13
        *@Param
        *@return
        **/

        return measureDataRepo.findAll(pageable);
    }


    public Page<MeasureData>  queryDynamic(Map<String,Object> reqMap, Pageable pageable)
    {
        /**
        *@Author 江成军
        *@Description 利用MongoTemplate，带动态条件查询分页
        *@Date 2019/02/09 16:13
        *@Param
        *@return
        **/

        //动态拼接查询条件
        Query query = new Query();
        if(!reqMap.get("poiname").toString().equals(""))//测点名称，模糊查询
        {
            Pattern pattern = Pattern.compile("^.*" + reqMap.get("poiname").toString()+ ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("poiname").regex(pattern));
        }
        if(!reqMap.get("datatype").toString().equals(""))//数据类型，精确查询
        {
            query.addCriteria(Criteria.where("datatype").is(reqMap.get("datatype").toString()));
        }

        //计算总数
        long total = mongoTemplate.count(query, MeasureData.class);

        query.with(pageable);
        List<MeasureData> list=mongoTemplate.find(query,MeasureData.class);
        Page<MeasureData> pages=new PageImpl(list,pageable,total);
        return pages;
    }


    @SystemAnnotationLog(actiondesc = "新添加测量数据(mongodb)")
    public void save(MeasureData measureData)
    {
        /**
        *@Author 江成军
        *@Description 单条保存记录
        *@Date 2019/02/12 10:43
        *@Param
        *@return
        **/

        mongoTemplate.insert(measureData);
    }

    public void batchSave(List<MeasureData> measureDatas)
    {
        /**
         *@Author 江成军
         *@Description 单条保存记录
         *@Date 2019/02/12 10:43
         *@Param
         *@return
         **/

        mongoTemplate.insert(measureDatas);
    }

    @SystemAnnotationLog(actiondesc = "删除现场测量信息")
    public String deleteByUuid(String uuid)
    {
        /**
        *@Author 江成军
        *@Description 利用MongoTemplate的remove进行记录的删除
        *@Date 2019/02/12 16:08
        *@Param
        *@return
        **/

        MeasureData measureData=measureDataRepo.findByUuid(uuid);

        Query q=new Query(new Criteria("uuid").is(uuid));
        mongoTemplate.remove(q,MeasureData.class);

        Map map = FilterPureEntity.getKeyAndValue(measureData);
        return JSON.toJSONString(measureData);
    }










}