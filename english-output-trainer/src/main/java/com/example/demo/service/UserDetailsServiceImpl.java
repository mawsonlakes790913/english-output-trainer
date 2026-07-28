package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserService userService;
	private final UserAccountService userAccountService;
	
	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		
		// ユーザー情報取得
        Users loginUser = userAccountService.getUserOne(userId);
        
        // ユーザーが存在しない場合
        if (loginUser == null) {
            throw new UsernameNotFoundException("user not found"); 
        }
        
        // ロールList作成
        GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole()); // ③
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        
        // UserDetails生成
        UserDetails userDetails = new User(loginUser.getUserId(), // A
                loginUser.getPassword(),
                authorities);

        return userDetails;
	}
}