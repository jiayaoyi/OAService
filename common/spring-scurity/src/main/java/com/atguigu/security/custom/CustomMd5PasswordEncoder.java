package com.atguigu.security.custom;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.atguigu.common.utils.MD5;


/**
 * <p>
 * 密码处理
 * </p>
 *
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {
	@Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}