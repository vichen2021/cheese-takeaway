package com.cheese.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoppingCartDTO implements Serializable {

    private Long dishId;

    private Long setmealId;

    private Long merchantId;

    private String dishFlavor;

}
