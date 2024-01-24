package com.hotel.hotelapplication.service;

import com.hotel.hotelapplication.exception.RoleAlreadyExsistException;
import com.hotel.hotelapplication.model.Role;
import com.hotel.hotelapplication.model.User;
import com.hotel.hotelapplication.repositoriy.RoleRepository;
import com.hotel.hotelapplication.repositoriy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role cretaeRole(Role theRole) {
        String roleName="ROLE_"+theRole.getName().toUpperCase();
        Role role=new Role(roleName);
        if(roleRepository.existsByName(role)){
            throw new RoleAlreadyExsistException(theRole.getName()+" role already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user=userRepository.findById(userId);
       Optional<Role> role=roleRepository.findById(roleId);
        if(role.isPresent() && role.get().getUsers().contains(user.get())){
                role.get().removeUserFromRole(user.get());
                roleRepository.save(role.get());
                return user.get();
        }
        throw new UsernameNotFoundException("User not found!");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<Role> role=roleRepository.findById(roleId);
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new RoleAlreadyExsistException(
                    user.get().getFirstname()+" is already to the "+ role.get().getName() +"role"
            );
        }
        if(role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role=roleRepository.findById(roleId);
        role.get().removeAllUsersFromRole();

        return roleRepository.save(role.get());
    }
}
