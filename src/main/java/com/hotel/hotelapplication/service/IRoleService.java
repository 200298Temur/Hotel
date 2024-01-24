package com.hotel.hotelapplication.service;

import com.hotel.hotelapplication.model.Role;
import com.hotel.hotelapplication.model.User;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role cretaeRole(Role theRole);
    void deleteRole(Long id);
    Role findByName(String name);
    User removeUserFromRole(Long userId,Long roleId);
    User assignRoleToUser(Long userId,Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
