/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.repository;

import co.th.linksinnovation.mirtphol.lms.model.UserDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, String>{

    public List<UserDetails> findByNameEnLikeOrNameThLike(String string, String string0);

    public List<UserDetails> findByNameEnIgnoreCaseContaining(String keyword);

    public List<UserDetails> findByNameEnIgnoreCaseContainingOrNameThContaining(String word, String word0);
    
}
