/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.repository;

import java.util.Collection;
import net.easyweb24.actionbot.entity.Role;
import net.easyweb24.actionbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zbigniewwilgosz
 */
@Repository
public interface RoleRepository extends JpaRepository < Role, Long > {
    Collection<Role> findByName(String name);
}
