package cherish.backend.member.service;

import cherish.backend.auth.jwt.JwtTokenProvider;
import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.common.service.RedisService;
import cherish.backend.member.email.EmailCode;
import cherish.backend.member.email.EmailService;
import cherish.backend.member.repository.MemberRepository;
import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cherish.backend.member.model.enums.Role.ADMIN;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RedisService redisService;

    @Transactional
    public TokenInfo login(String email, String password, Boolean isPersist) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication,isPersist);

        return tokenInfo;
    }

    @Transactional
    public String join(MemberFormDto memberFormDto) {
        boolean isAlready = memberRepository.existsByEmail(memberFormDto.getEmail());
        if (isAlready == true){
            throw new IllegalArgumentException("이미 이메일이 등록되어 있습니다.");
        }
        else {
            Member savedMember = memberRepository.save(Member.createMember(memberFormDto, passwordEncoder));
            return savedMember.getEmail();
        }
    }

    @Transactional
    public void delete(String email, String nowUserEmail) {
        Member changeMember = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        Member nowMember = memberRepository.findByEmail(nowUserEmail).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        if ( (nowMember.getRoles().equals(ADMIN)) || (changeMember.equals(nowMember))){
            Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
            memberRepository.delete(member);
        }

    }

    public Boolean isMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void changePwd(String email, String pwd,String nowUserEmail) {
        Member changeMember = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        Member nowMember = memberRepository.findByEmail(nowUserEmail).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        if ( (nowMember.getRoles().equals(ADMIN)) || (changeMember.equals(nowMember))){
            changeMember.changePwd(pwd,passwordEncoder);
        }
        else{
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    public String sendEmailCode(String email){
        if(!isMember(email)){
            String code = EmailCode.createCode().getCode();
            emailService.sendMessage(email, code);
            redisService.setRedisCode(email,code);
            return code;
        } else
            throw new IllegalArgumentException("이미 가입한 사용자 입니다.");
    }

    public boolean validEmailCode(String email, String inputCode){
        return redisService.validCode(email,inputCode);
    }
}
