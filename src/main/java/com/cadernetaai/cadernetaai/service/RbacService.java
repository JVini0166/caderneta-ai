package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Role;
import com.cadernetaai.cadernetaai.domain.WorkspaceUser;
import com.cadernetaai.cadernetaai.repository.WorkspaceUserRepository;
import java.util.EnumSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RbacService {
    private static final Logger logger = LoggerFactory.getLogger(RbacService.class);
    
    private final WorkspaceUserRepository workspaceUserRepository;
    public RbacService(WorkspaceUserRepository workspaceUserRepository) { this.workspaceUserRepository = workspaceUserRepository; }

    private static final Set<Role> CAN_MANAGE_PROJECTS = EnumSet.of(Role.SYSTEM_ADMIN, Role.MAYOR_ACCESS);
    private static final Set<Role> CAN_SIGN_REPORTS = EnumSet.of(Role.SYSTEM_ADMIN, Role.ENGINEER);
    private static final Set<Role> CAN_LOG_DAILY = EnumSet.of(Role.SYSTEM_ADMIN, Role.MASTER_BUILDER);
    private static final Set<Role> CAN_SELF_LOG = EnumSet.of(Role.WORKER);
    private static final Set<Role> CAN_MANAGE_TEAM_OR_CATALOG = EnumSet.of(Role.SYSTEM_ADMIN, Role.MAYOR_ACCESS);
    private static final Set<Role> CAN_MANAGE_PLANNING = EnumSet.of(Role.SYSTEM_ADMIN, Role.ENGINEER);
    private static final Set<Role> CAN_UPLOAD_DOCS = EnumSet.of(Role.SYSTEM_ADMIN, Role.MAYOR_ACCESS, Role.ENGINEER, Role.MASTER_BUILDER);

    public boolean canCreateOrEditProject(Long workspaceId, Long userId) {
        return isAnyRole(workspaceId, userId, CAN_MANAGE_PROJECTS);
    }
    public boolean canManageProjects(Long workspaceId, Long userId) { return canCreateOrEditProject(workspaceId, userId); }
    public boolean canSignReports(Long workspaceId, Long userId) { return isAnyRole(workspaceId, userId, CAN_SIGN_REPORTS); }
    public boolean canRegisterDailyLog(Long workspaceId, Long userId) { return isAnyRole(workspaceId, userId, CAN_LOG_DAILY) || isAnyRole(workspaceId, userId, CAN_SELF_LOG); }
    public boolean canManageTeamOrCatalog(Long workspaceId, Long userId) { return isAnyRole(workspaceId, userId, CAN_MANAGE_TEAM_OR_CATALOG); }
    public boolean canManagePlanning(Long workspaceId, Long userId) { return isAnyRole(workspaceId, userId, CAN_MANAGE_PLANNING); }
    public boolean canUploadDocuments(Long workspaceId, Long userId) { return isAnyRole(workspaceId, userId, CAN_UPLOAD_DOCS); }

    private boolean isAnyRole(Long workspaceId, Long userId, Set<Role> roles) {
        try {
            logger.debug("Checking role for user {} in workspace {}", userId, workspaceId);
            return workspaceUserRepository.findByWorkspace_IdAndUser_Id(workspaceId, userId)
                    .map(WorkspaceUser::getRole)
                    .map(role -> {
                        logger.debug("User {} has role: {}", userId, role);
                        return role;
                    })
                    .map(String::toUpperCase)
                    .map(role -> {
                        try {
                            return Role.valueOf(role);
                        } catch (IllegalArgumentException e) {
                            logger.error("Invalid role '{}' for user {} in workspace {}", role, userId, workspaceId, e);
                            throw e;
                        }
                    })
                    .map(roles::contains)
                    .orElse(false);
        } catch (Exception e) {
            logger.error("Error checking role for user {} in workspace {}", userId, workspaceId, e);
            throw e;
        }
    }
}


