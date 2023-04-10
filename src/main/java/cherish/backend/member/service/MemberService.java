package cherish.backend.member.service;

import cherish.backend.auth.jwt.JwtTokenProvider;
import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.common.service.RedisService;
import cherish.backend.member.constant.Constants;
import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.dto.MemberInfoResponse;
import cherish.backend.member.dto.redis.EmailVerificationInfoDto;
import cherish.backend.member.email.util.EmailCodeGenerator;
import cherish.backend.member.email.service.EmailService;
import cherish.backend.member.model.Job;
import cherish.backend.member.model.Member;
import cherish.backend.member.repository.JobRepository;
import cherish.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public TokenInfo login(String email, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public Long join(MemberFormDto memberFormDto) {
        boolean isAlready = memberRepository.existsByEmail(memberFormDto.getEmail());
        if (isAlready) {
            throw new IllegalStateException(Constants.EMAIL_ALREADY);
        }
        // 회원가입 시 인증된 이메일인지 검증하는 로직 추가
        if (!isVerifiedEmail(memberFormDto.getEmail())) {
            throw new IllegalArgumentException("이메일 인증 정보가 없거나 만료되었습니다. 이메일 인증을 다시 해주세요");
        }
        Member savedMember = memberRepository.save(Member.createMember(memberFormDto, passwordEncoder));
        return savedMember.getId();
    }

    @Transactional
    public void delete(String loginUserEmail) {
        Member member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new UsernameNotFoundException(Constants.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }

    public boolean isMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void changePwd(String email, String pwd) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(Constants.MEMBER_NOT_FOUND));
        member.changePwd(pwd, passwordEncoder);
    }

    public String sendEmailCode(String email) {
        if (!isMember(email)) {
            String code = EmailCodeGenerator.generateCode();
            emailService.sendMessage(email, "인증번호 : " + code);
            setRedisCode(email, code, 5 * 60);
            log.info("code {} has been sent to {}", code, email);
            return code;
        } else
            throw new IllegalStateException(Constants.EMAIL_ALREADY);
    }

    public void setRedisCode(String email, String validCode, int second) {
        if (redisService.hasEmailCodeKey(email))
            throw new IllegalStateException(second + "초 내에 이메일을 재전송 할 수 없습니다.");

        redisService.setEmailCode(email, validCode, second);
        redisService.setEmailVerified(email, false, second);
        log.info("input = {} ", validCode);
    }

    public boolean validEmailCode(String email, String inputCode) {
        if (!redisService.hasEmailCodeKey(email))
            throw new IllegalStateException("이메일 인증 코드를 발송한 내역이 없습니다.");

        String validCode = redisService.getEmailCode(email);
        if (!inputCode.equals(validCode))
            throw new IllegalStateException("입력한 입력코드가 다릅니다.");
        // redis에 인증 완료된 상태로 10분간 저장
        redisService.setEmailVerified(email, true, 10 * 60);
        return true;
    }

    @Transactional
    public Long changeInfo(String nickName, String jobName, String loginUserEmail) {
        Member member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new IllegalStateException(Constants.MEMBER_NOT_FOUND));
        Job job = jobRepository.findByName(jobName).orElseThrow(() -> new IllegalStateException("해당 직업이 존재하지 않습니다."));
        member.changeInfo(nickName, job);
        return member.getId();
    }

    public MemberInfoResponse getInfo(String loginUserEmail) {
        Member member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new IllegalStateException(Constants.MEMBER_NOT_FOUND));
        return MemberInfoResponse.of(member);

    }

    private boolean isVerifiedEmail(String email) {
        if (!redisService.hasVerifiedKey(email)) {
            return false;
        }
        return redisService.isEmailVerified(email);
    }
}
