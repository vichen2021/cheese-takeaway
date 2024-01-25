package com.cheese.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.entity.DishFlavor;
import com.cheese.mapper.DishFlavorMapper;
import com.cheese.service.DishFlavorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */
@Service
@Transactional
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
