package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.example.projects.lovable_clone.dto.member.MemberResponse;
import com.example.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.example.projects.lovable_clone.entity.Project;
import com.example.projects.lovable_clone.entity.ProjectMember;
import com.example.projects.lovable_clone.entity.ProjectMemberId;
import com.example.projects.lovable_clone.entity.User;
import com.example.projects.lovable_clone.mapper.ProjectMapper;
import com.example.projects.lovable_clone.mapper.ProjectMemberMapper;
import com.example.projects.lovable_clone.repo.ProjectMemberRepository;
import com.example.projects.lovable_clone.repo.ProjectRepository;
import com.example.projects.lovable_clone.repo.UserRepository;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

ProjectMemberRepository projectMemberRepository;
ProjectRepository projectRepository;
ProjectMemberMapper projectMemberMapper;
UserRepository userRepository;
AuthUtil authUtil;

    @Override
    @PreAuthorize("@security.canViewMembers(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId);

        return projectMemberRepository.findByIdProjectId(projectId)
                        .stream()
                        .map(projectMemberMapper::toProjectMemberResponseFromMember)
                        .toList()
        ;
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId);

        User invitee = userRepository.findByUsername(request.username()).orElseThrow();

        if(invitee.getId().equals(userId)){
            throw new RuntimeException("cannot invite yourself");
        }
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.getId());
        if(projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("cannot invite once again");
        }

        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();
        projectMemberRepository.save(member);
        return projectMemberMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId);

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);
        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public void removeProjectMember(Long projectId, Long memberId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId);

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Member not found in project");
        }
        projectMemberRepository.deleteById(projectMemberId);
    }

    //Internal function
    public Project getAccessibleProjectById(Long projectId)
    {
        Long userId = authUtil.getCurrentUserId();
        return projectRepository.findAccessibleProjectById(projectId,userId).orElseThrow();
    }
}
