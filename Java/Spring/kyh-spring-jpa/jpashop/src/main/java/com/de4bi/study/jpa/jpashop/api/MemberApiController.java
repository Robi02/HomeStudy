package com.de4bi.study.jpa.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.de4bi.study.jpa.jpashop.domain.Member;
import com.de4bi.study.jpa.jpashop.service.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result<MemberDto> memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
            .map(m -> new MemberDto(m.getName()))
            .collect(Collectors.toList());
        return new Result(collect);
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse savememberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member newMember = new Member();
        newMember.setName(request.getName());
        Long id = memberService.join(newMember);
        return new CreateMemberResponse(id);
    }
    
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
        @PathVariable("id") Long id,
        @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(id, findMember.getName());
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class CreateMemberRequest {
        private String name;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class UpdateMemberRequest {
        private String name;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}
