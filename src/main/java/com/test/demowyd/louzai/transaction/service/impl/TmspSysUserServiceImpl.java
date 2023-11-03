package com.test.demowyd.louzai.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.demowyd.louzai.transaction.dao.TmspSysUserDao;
import com.test.demowyd.louzai.transaction.entity.TmspSysUser;
import com.test.demowyd.louzai.transaction.service.TmspSysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (TmspSysUser)表服务实现类
 *
 * @author Stone
 * @since 2023-11-02 14:18:05
 */
@Service("tmspSysUserService")
public class TmspSysUserServiceImpl extends ServiceImpl<TmspSysUserDao, TmspSysUser> implements TmspSysUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInTransactional(Long userId, String userName, boolean rollBackFlag) {
        TmspSysUser user = getById(userId);
        user.setUserName(userName);

        updateById(user);

        if (rollBackFlag) {
            throw new RuntimeException("请回滚一下~");
        }
    }
}

