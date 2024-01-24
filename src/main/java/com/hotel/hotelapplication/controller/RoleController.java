package com.hotel.hotelapplication.controller;

import com.hotel.hotelapplication.exception.RoleAlreadyExsistException;
import com.hotel.hotelapplication.model.Role;
import com.hotel.hotelapplication.model.User;
import com.hotel.hotelapplication.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
      return new ResponseEntity<>(roleService.getRoles(), HttpStatus.FOUND);
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole){
        try {
            roleService.cretaeRole(theRole);
                return ResponseEntity.ok("New role created successful");
        }catch (RoleAlreadyExsistException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());
        }
    }
    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
    }
    @PostMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUserFromRole(@PathVariable("roleId") Long roleId){
            return roleService.removeAllUsersFromRole(roleId);
    }
    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(
            @PathVariable("userId") Long userId,
            @PathVariable("roleId") Long roleId){
        return roleService.removeUserFromRole(userId,roleId);
    }
    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(
            @PathVariable("userId")Long userId,
            @PathVariable("roleId")Long roleId
    ){
        return roleService.assignRoleToUser(userId,roleId);
    }
 }



