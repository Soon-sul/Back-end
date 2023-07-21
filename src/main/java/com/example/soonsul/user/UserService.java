package com.example.soonsul.user;

import com.example.soonsul.config.s3.S3Uploader;
import com.example.soonsul.search.dto.SearchDto;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.repository.UserRepository;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserUtil userUtil;
    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket.url}")
    private String AWS_S3_BUCKET_URL;


    @Transactional
    public void putProfile(String nickname, MultipartFile image){
        final User user= userUtil.getUserByAuthentication();
        user.updateNickname(nickname);

        if(user.getProfileImage()!=null) s3Uploader.deleteFile(user.getProfileImage().substring(AWS_S3_BUCKET_URL.length()));
        if(image==null|| image.isEmpty()) user.updateProfileImage(null);
        else user.updateProfileImage(s3Uploader.upload(image,"user-profile"));
    }


    @Transactional(readOnly = true)
    public boolean getNicknameCheck(String nickname){
        final Optional<User> user= userRepository.findByNickname(nickname);
        return user.isEmpty();
    }

}
