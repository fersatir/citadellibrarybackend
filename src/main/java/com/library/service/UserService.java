package com.library.service;

import com.library.domain.Role;
import com.library.domain.User;
import com.library.domain.enums.RoleType;
import com.library.dto.UserDTO;
import com.library.dto.mapper.UserMapper;
import com.library.dto.requests.RegisterRequest;
import com.library.exception.ConflictException;
import com.library.dto.UserCreateDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;


    public UserCreateDTO createUser(UserCreateDTO userCreateDTO) {

        //TODO kayıt yapmaya çalışan kullanıcının role bilgisine göre (admin, employee), kayıt edilecek kullanıcıya role atanacak

        User user = userMapper.userCreateDTOToUser(userCreateDTO);

        Role ro = new Role();
        ro.setName(roleRepository.findById(userCreateDTO.getRoleId()).get().getName());
        Set<Role> roles = new HashSet<>();
        roles.add(ro);

        userRepository.save(user);

        userCreateDTO.setId(user.getId());

        return userCreateDTO;

    }

    public UserDTO register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,request.getEmail()));}

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Role role= roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_MEMBER.name())));


        Set <Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setPassword(encodedPassword);// security eklendikten sonra hashlenmiş password set edilecek
        user.setBirthDate(request.getBirthDate());
        user.setCreateDate(request.getCreateDate());
        user.setPhone(request.getPhone());
        user.setResetPasswordCode(request.getResetPasswordCode());
        user.setRoles(roles);
     /*   UserDTO userDTO= userMapper.registerToUserDTO(request);
        userDTO.setRoles(roles);
        User user = userMapper.userDTOToUser(userDTO);*/
        userRepository.save(user);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return userDTO;

    }

}
