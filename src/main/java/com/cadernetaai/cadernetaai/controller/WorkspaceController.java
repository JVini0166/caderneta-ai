package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.domain.WorkspaceUser;
import com.cadernetaai.cadernetaai.repository.WorkspaceUserRepository;
import com.cadernetaai.cadernetaai.controller.security.AuthUtil;
import com.cadernetaai.cadernetaai.service.RbacService;
import com.cadernetaai.cadernetaai.service.WorkspaceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {
    private static final Logger logger = LoggerFactory.getLogger(WorkspaceController.class);
    
    private final WorkspaceService workspaceService;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final RbacService rbacService;
    public WorkspaceController(WorkspaceService workspaceService, WorkspaceUserRepository workspaceUserRepository, RbacService rbacService) {
        this.workspaceService = workspaceService;
        this.workspaceUserRepository = workspaceUserRepository;
        this.rbacService = rbacService;
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> list() {
        return ResponseEntity.ok(workspaceService.list());
    }

    @PostMapping
    public ResponseEntity<Workspace> create(@Valid @RequestBody Workspace body) {
        return ResponseEntity.ok(workspaceService.create(body));
    }

    // Lista todos os usuários de todos os workspaces aos quais o solicitante pertence,
    // somente para SYSTEM_ADMIN ou MAYOR_ACCESS
    @GetMapping("/listUsers")
    public ResponseEntity<List<Map<String, Object>>> listUsers() {
        logger.info("Starting listUsers request");
        
        Long requesterUserId = AuthUtil.currentUserId();
        logger.info("Requester user ID: {}", requesterUserId);
        if (requesterUserId == null) throw new SecurityException("Missing or invalid token");

        List<WorkspaceUser> userWorkspaces = workspaceUserRepository.findByUserId(requesterUserId);
        logger.info("Found {} workspace links for user {}", userWorkspaces.size(), requesterUserId);
        
        boolean allowed = false;
        for (WorkspaceUser link : userWorkspaces) {
            logger.debug("Checking workspace {} for user {}", link.getWorkspace().getId(), requesterUserId);
            if (rbacService.canManageTeamOrCatalog(link.getWorkspace().getId(), requesterUserId)) {
                allowed = true;
                logger.info("User {} has permission for workspace {}", requesterUserId, link.getWorkspace().getId());
                break;
            }
        }
        if (!allowed) {
            logger.warn("User {} not allowed to list users", requesterUserId);
            throw new SecurityException("Not allowed: only SYSTEM_ADMIN or MAYOR_ACCESS can list users");
        }
        
        // agrega usuários de todos os workspaces do solicitante
        java.util.Set<Long> workspaceIds = new java.util.HashSet<>();
        for (WorkspaceUser link : userWorkspaces) {
            workspaceIds.add(link.getWorkspace().getId());
        }
        logger.info("Collecting users from {} workspaces: {}", workspaceIds.size(), workspaceIds);
        
        java.util.List<Map<String, Object>> result = new java.util.ArrayList<>();
        for (Long wsId : workspaceIds) {
            List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findByWorkspace_Id(wsId);
            logger.debug("Found {} users in workspace {}", workspaceUsers.size(), wsId);
            
            // Pega as informações do workspace (usando o primeiro usuário como referência)
            WorkspaceUser firstUser = workspaceUsers.get(0);
            Workspace workspace = firstUser.getWorkspace();
            
            // Cria a lista de usuários para este workspace
            java.util.List<Map<String, Object>> users = new java.util.ArrayList<>();
            for (WorkspaceUser wu : workspaceUsers) {
                try {
                    users.add(Map.of(
                            "userId", wu.getUser().getId(),
                            "fullName", wu.getUser().getFullName(),
                            "email", wu.getUser().getEmail(),
                            "role", wu.getRole(),
                            "status", wu.getStatus()
                    ));
                } catch (Exception e) {
                    logger.error("Error processing user {} in workspace {}: {}", wu.getUser().getId(), wsId, e.getMessage(), e);
                    throw e;
                }
            }
            
            // Cria o objeto workspace com suas informações e usuários
            Map<String, Object> workspaceData = Map.of(
                    "workspaceId", wsId,
                    "name", workspace.getName(),
                    "cnpj", workspace.getCnpj() != null ? workspace.getCnpj() : "",
                    "city", workspace.getCity() != null ? workspace.getCity() : "",
                    "state", workspace.getState() != null ? workspace.getState() : "",
                    "users", users
            );
            
            result.add(workspaceData);
        }
        
        logger.info("Returning {} workspaces with users", result.size());
        return ResponseEntity.ok(result);
    }
}


