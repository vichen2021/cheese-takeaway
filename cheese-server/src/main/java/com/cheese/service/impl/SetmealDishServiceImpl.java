package com.cheese.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.entity.SetmealDish;
import com.cheese.mapper.SetmealDishMapper;
import com.cheese.service.SetmealDishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/29 23:02
 * @projectName cheese-takeaway
 */
@Service
@Transactional
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish>implements SetmealDishService{

}

