package com.lirlo.baseplat.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lirlo.baseplat.system.mapper.SystemMapper;
import com.lirlo.baseplat.system.pojo.T_SYS_ACCOUNT;
import com.lirlo.baseplat.system.service.SystemService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lirlo
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    SystemMapper systemMapper;

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Resource
    RedissonClient redissonClient;

//    @Autowired
//    DefaultMQProducer defaultMQProducer;

    @Override
    public IPage<T_SYS_ACCOUNT> selectPage(T_SYS_ACCOUNT entity,int pageNum, int pageSize){

//        stringRedisTemplate.opsForValue().get();

        IPage<T_SYS_ACCOUNT> accountiPage = new Page<>(pageNum,pageSize);
        return this.systemMapper.selectPage(accountiPage, Wrappers.lambdaQuery());
    }

    @Override
    @Transactional(readOnly = true)
    public T_SYS_ACCOUNT getOne() {

        RLock rLock = redissonClient.getLock("user");

        rLock.lock();

        ObjectMapper objectMapper = new ObjectMapper();
        T_SYS_ACCOUNT info = objectMapper.convertValue(redisTemplate.opsForValue().get("user"),T_SYS_ACCOUNT.class);
        if(info==null){

            QueryWrapper<T_SYS_ACCOUNT> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(T_SYS_ACCOUNT::getLoginName,"wipadmin");
            T_SYS_ACCOUNT t_sys_account = this.systemMapper.selectOne(queryWrapper);

            redisTemplate.opsForValue().set("user",t_sys_account);
            return t_sys_account;
        }

        rLock.unlock();
//        redissonClient.shutdown();

//        redisTemplat e.expire("user",100, TimeUnit.SECONDS);
//        redisTemplate.delete("user");

//        Message message = new Message("topic-mq", "user", info.toString().getBytes());
//
//        try {
//            SendResult send = defaultMQProducer.send(message);
//            System.out.println("发送消息"+send.getMsgId()+send.getSendStatus());
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        } catch (RemotingException e) {
//            e.printStackTrace();
//        } catch (MQBrokerException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        defaultMQProducer.shutdown();
        return info;
    }


}
