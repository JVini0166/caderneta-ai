package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.WorkspaceUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {
    
    @Query("SELECT wu FROM WorkspaceUser wu JOIN FETCH wu.user JOIN FETCH wu.workspace WHERE wu.user.id = :userId")
    List<WorkspaceUser> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT wu FROM WorkspaceUser wu JOIN FETCH wu.user JOIN FETCH wu.workspace WHERE wu.workspace.id = :workspaceId AND wu.user.id = :userId")
    Optional<WorkspaceUser> findByWorkspace_IdAndUser_Id(@Param("workspaceId") Long workspaceId, @Param("userId") Long userId);
    
    @Query("SELECT wu FROM WorkspaceUser wu JOIN FETCH wu.user JOIN FETCH wu.workspace WHERE wu.workspace.id = :workspaceId")
    List<WorkspaceUser> findByWorkspace_Id(@Param("workspaceId") Long workspaceId);
}


