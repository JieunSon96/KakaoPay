
package com.kakaopay.kakao.service;

import com.kakaopay.kakao.model.user.User;
import com.kakaopay.kakao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 이메일을 통한 유저 존재 확인
    public boolean existsByEmail(String email){
        boolean result = false;
        try{
            result=userRepository.existsByEmail(email);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return result;
        }
    }

    // 유저 생성
    public User createUser(User user){
        User createUser=new User();

        try{
            createUser=userRepository.save(user);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return createUser;
        }
    }

    // 모든 유저 목록 출력
    public List<User> getAllUser() {
        List<User> users = new LinkedList<>();
        try {
            users = userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    // 유저 검색
    public Optional<User> findByEmail(String email){
        Optional<User> user= userRepository.findByEmail(email);
        return user;
    }



}

