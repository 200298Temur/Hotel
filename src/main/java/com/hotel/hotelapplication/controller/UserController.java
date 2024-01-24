package com.hotel.hotelapplication.controller;

import com.hotel.hotelapplication.model.User;
import com.hotel.hotelapplication.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

        @GetMapping("/all")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<List<User>> getUsers(){
            return new ResponseEntity<>(userService.getUsers(), HttpStatus.NOT_FOUND);
        }

        @GetMapping("/{email}")
        @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN') ")
        public ResponseEntity<?> getUserBYEmail(@PathVariable("email") String  email){
            try{
                User theUser=userService.getUser(email);
                return ResponseEntity.ok(theUser);
            }catch (UsernameNotFoundException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
            }
        }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER') and #email == principal.username")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String email){
        try{
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

}
