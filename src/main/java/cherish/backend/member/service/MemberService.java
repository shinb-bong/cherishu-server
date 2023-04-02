package cherish.backend.member.service;

import cherish.backend.auth.jwt.JwtTokenProvider;
import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.common.service.RedisService;
import cherish.backend.member.dto.MemberInfoResponse;
import cherish.backend.member.email.EmailCode;
import cherish.backend.member.email.EmailService;
import cherish.backend.member.model.Job;
import cherish.backend.member.repository.JobRepository;
import cherish.backend.member.constant.Constants;
import cherish.backend.member.repository.MemberRepository;
import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cherish.backend.member.model.enums.Role.ADMIN;

@Slf4j
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
    private final JobRepository jobRepository;

    @Transactional
    public TokenInfo login(String email, String password, Boolean isPersist) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication, isPersist);
    }

    @Transactional
    public Long join(MemberFormDto memberFormDto) {
        boolean isAlready = memberRepository.existsByEmail(memberFormDto.getEmail());
        if (isAlready){
            throw new IllegalStateException(Constants.EMAIL_ALREADY);
        }
        else {
            Member savedMember = memberRepository.save(Member.createMember(memberFormDto, passwordEncoder));
            return savedMember.getId();
        }
    }

    @Transactional
    public void delete(String email, String nowUserEmail) {
        Member changeMember = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(Constants.MEMBER_NOT_FOUND));
        Member nowMember = memberRepository.findByEmail(nowUserEmail).orElseThrow(() -> new UsernameNotFoundException(Constants.MEMBER_NOT_FOUND));
        if ( (nowMember.getRoles().equals(ADMIN)) || (changeMember.equals(nowMember))){
            Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(Constants.MEMBER_NOT_FOUND));
            memberRepository.delete(member);
        }

    }

    public boolean isMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void changePwd(String email,String pwd) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(Constants.MEMBER_NOT_FOUND));
        member.changePwd(pwd,passwordEncoder);
    }

    public String sendEmailCode(String email){
        if(!isMember(email)){
            String code = EmailCode.createCode().getCode();
            log.info("make code =  {}", code);
            emailService.sendMessage(email, code);
            redisService.setRedisCode(email,code,30L);
            return code;
        } else
            throw new IllegalStateException(Constants.EMAIL_ALREADY);
    }

    public boolean validEmailCode(String email, String inputCode){
        return redisService.validCode(email,inputCode);
    }

    @Transactional
    public Long changeInfo(String nickName, String jobName, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException(Constants.MEMBER_NOT_FOUND));
        Job job = jobRepository.findByName(jobName).orElseThrow(() -> new IllegalStateException("해당 직업이 존재하지 않습니다."));
        member.changeInfo(nickName,job);
        return member.getId();
    }

    public MemberInfoResponse getInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException(Constants.MEMBER_NOT_FOUND));
        return MemberInfoResponse.of(member);

    }
}
