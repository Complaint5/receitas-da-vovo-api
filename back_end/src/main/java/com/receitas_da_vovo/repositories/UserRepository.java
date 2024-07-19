package com.receitas_da_vovo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.receitas_da_vovo.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserByIdAndActivatedTrue(UUID id);
    List<UserEntity> findAllUsersByActivatedTrue();
    
    // TODO: javadoc
}
