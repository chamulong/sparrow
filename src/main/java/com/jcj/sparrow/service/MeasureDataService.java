package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.MeasureData;
import com.jcj.sparrow.repository.MeasureDataRepo;
import com.jcj.sparrow.systemaop.SystemAnnotationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author: 江成军
 * @Date: 2019/01/30 15:08
 * @Description: 测量信息业务层，对应MongoDB
 */
@Service
public class MeasureDataService
{
    @Autowired
    private MeasureDataRepo measureDataRepo;

    @SystemAnnotationLog(actiondesc = "mongodb添加数据")
    public void save(MeasureData measureData)
    {
        measureDataRepo.save(measureData);
    }

    //基本的查询分页,没有查询条件
    public Page<MeasureData> basicPageList(Pageable pageable)
    {
        return measureDataRepo.findAll(pageable);
    }








}