package com.lirlo.baseplat.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lirlo.baseplat.system.pojo.T_SYS_ACCOUNT;

public interface SystemService {

    public IPage<T_SYS_ACCOUNT> selectPage(T_SYS_ACCOUNT entity,int pageNum, int pageSize);

    public T_SYS_ACCOUNT getOne();
}
