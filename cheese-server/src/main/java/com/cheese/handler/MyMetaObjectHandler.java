package com.cheese.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cheese.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert");
        if(metaObject.hasGetter("createTime")){
            metaObject.setValue("createTime", LocalDateTime.now());
        }
        if(metaObject.hasGetter("createUser")){
            metaObject.setValue("createUser", (Long)BaseContext.getCurrentId());
        }
        if(metaObject.hasGetter("updateTime")){
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
        if(metaObject.hasGetter("updateUser")){
            metaObject.setValue("updateUser", (Long)BaseContext.getCurrentId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", (Long)BaseContext.getCurrentId());
    }
}
