//package com.example.soonsul.user;
//
//import com.example.soonsul.user.entity.User;
//import com.example.soonsul.user.exception.UserNotExist;
//import com.example.soonsul.user.repository.UserRepository;
//import com.example.soonsul.util.UserUtil;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @InjectMocks
//    private UserUtil userUtil;
//
//    @Mock
//    private UserRepository userRepository;
//
//
//    @Nested
//    class 현재_접속중인_유저_조회{
//        @Test
//        void 성공(){
//            //given
//            final String userId= "user1";
//            createAuthentication(userId);
//
//            doReturn(Optional.of(user(userId))).when(userRepository).findById(userId);
//
//
//            //when
//            final User getUser= userUtil.getUserByAuthentication();
//
//
//            //then
//            assertThat(getUser.getUserId()).isEqualTo(userId);
//        }
//
//        @Test
//        void 실패(){
//            //given
//            final String userId= "user1";
//            createAuthentication(userId);
//
//            //when, then
//            assertThatThrownBy(() -> userUtil.getUserByAuthentication())
//                    .isInstanceOf(UserNotExist.class)
//                    .hasMessageContaining("login user not exist");
//        }
//    }
//
//
//
//    private void createAuthentication(String userId){
//        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                userId,
//                null,
//                AuthorityUtils.NO_AUTHORITIES
//        );
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(authentication);
//        SecurityContextHolder.setContext(securityContext);
//    }
//
//    private User user(String userId){
//        return User.builder()
//                .userId(userId)
//                .build();
//    }
//}
