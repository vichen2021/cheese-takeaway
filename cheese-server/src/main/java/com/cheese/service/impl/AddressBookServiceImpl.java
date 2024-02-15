package com.cheese.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.entity.AddressBook;
import com.cheese.mapper.AddressBookMapper;
import com.cheese.service.AddressBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */
@Service
@Transactional
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
