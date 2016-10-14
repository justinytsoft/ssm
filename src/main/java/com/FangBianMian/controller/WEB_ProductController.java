package com.FangBianMian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.service.IProductService;

/**
 * 商城商品类
 * @author Administrator
 *
 */
@RequestMapping("/web/product")
@ResponseBody
public class WEB_ProductController {

	@Autowired
	private IProductService productService;
}
