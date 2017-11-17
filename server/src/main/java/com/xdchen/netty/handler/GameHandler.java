package com.xdchen.netty.handler;

import com.xdchen.netty.model.GameRequest;
import com.xdchen.netty.model.GameResponse;

/**
* @project:		demo
* @Title:		GameHandler.java
* @Package:		cpgame.demo.handler
  @author: 		chenpeng
* @email: 		46731706@qq.com
* @date:		2015年8月20日 下午2:25:51 
* @description:
* @version:
*/

public interface GameHandler
{
  void execute(GameRequest paramGameRequest, GameResponse paramGameResponse);
}
