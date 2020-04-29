package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        //获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //判断手机号和密码是否为空
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"用户名或密码不能为空");
        }
        //根据手机号去数据库查询
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断是否从数据库中查询出结果
        if(mobileMember == null){
            throw new GuliException(20001,"账号不存在");
        }
        //根据手机号查询出来的数据，判断密码是否一直
        String MD5Password = MD5.encrypt(password);
        if(!MD5Password.equals(mobileMember.getPassword())){
            throw new GuliException(20001,"密码错误");
        }
        //登录成功,将ID和nikeName生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();//手机号
        String nikeName = registerVo.getNikeName();//昵称
        String password = registerVo.getPassword();//密码

        //判断传输的数据中是否有空值，有空值不能进行注册操作
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(nikeName)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"信息不全，注册失败！！！");
        }

        //判断注册码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!redisCode.equals(code)){
            throw new GuliException(20001,"注册码错误，注册失败！！！");
        }

        //判断手机号是否存在，若存在则不能注册
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new GuliException(20001,"号码已存在，注册失败！！！");
        }

        //注册用户，将数据存到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nikeName);
        member.setIsDisabled(false);//不禁用

        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getMemberByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    //统计每天的注册人数
    @Override
    public Integer dayRegisterCount(String day) {
        Integer count = baseMapper.getRegisterCountDay(day);
        return count;
    }
}
