package com.cheese.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheese.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
