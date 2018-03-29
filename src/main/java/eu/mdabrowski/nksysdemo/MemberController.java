package eu.mdabrowski.nksysdemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping
    public Map<String, List<Member>> findAll() {
        return wrapList("members", memberRepository.findAll());
    }

    @GetMapping("/{id}")
    public Map<String, Member> find(@PathVariable Long id) {
        return wrap("member", memberRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @PostMapping
    public Map<String, Member> create(@RequestBody Member member) {
        return wrap("member", memberRepository.save(member));
    }

    private Map<String, Member> wrap(String entityName, Member member) {
        Map<String, Member> map = new HashMap<>();
        map.put(entityName, member);
        return map;
    }

    private Map<String, List<Member>> wrapList(String entityName, List<Member> members) {
        Map<String, List<Member>> map = new HashMap<>();
        map.put(entityName, members);
        return map;
    }
}
