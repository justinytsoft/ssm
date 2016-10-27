package com.FangBianMian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/game")
@Controller
public class GameController {

	@RequestMapping("/shot")
	public String shot(){
		return "game/shot/index";
	}
}
