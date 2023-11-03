package com.test.demowyd.louzai.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demowyd.louzai.transaction.entity.TmspSysUser;

/**
 * (TmspSysUser)表服务接口
 *
 * @author Stone
 * @since 2023-11-02 14:18:04
 */
public interface TmspSysUserService extends IService<TmspSysUser> {

    void updateInTransactional(Long userId, String userName, boolean rollBackFlag);
}

