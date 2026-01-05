
package com.vehicle.management.service;

import com.vehicle.management.dto.LoginDTO;
import com.vehicle.management.vo.LoginVO;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 用户登出
     * @param token Token
     */
    void logout(String token);

    /**
     * 获取当前登录用户信息
     * @param token Token
     * @return 用户信息
     */
    LoginVO getCurrentUser(String token);
}
