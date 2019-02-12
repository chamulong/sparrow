package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.MeasureData;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 江成军
 * @Date 2019/01/30 11:16
 * @Description 测量信息访问接口，对应MongoDB
 */
@Repository
public interface MeasureDataRepo extends MongoRepository<MeasureData,ObjectId>
{
    //根据类别进行查询
    List<MeasureData> findByDatatype(String datatype);

    //带分页、条件的查询(暂时没用到)
    @Query(value="{'poiname':{'$regex':?0},'datatype':?1}")
    Page<MeasureData> queryByPoinameAndByDatatype(String poiname,String datatype,Pageable pageable);

    //根据uuid查询对应的用户实体
    MeasureData findByUuid(String uuid);
}
