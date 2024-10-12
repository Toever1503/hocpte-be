package hocpte.services;

import hocpte.configs.jwt.JwtLoginResponse;
import hocpte.configs.jwt.JwtUserLoginModel;
import hocpte.dtos.UserProfileDto;
import hocpte.entities.UserEntity;
import hocpte.models.ChangePasswordModel;
import hocpte.models.CustomUserRegister;
import hocpte.models.UserModel;
import hocpte.models.UserProfileModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService extends IBaseService<UserEntity, UserProfileDto, UserModel, Long>{

    UserProfileDto add(CustomUserRegister customUserRegister);

    JwtLoginResponse logIn(JwtUserLoginModel model);

    boolean tokenFilter(String token, HttpServletRequest req, HttpServletResponse res);

    boolean changeMyAvatar(MultipartFile file);

    boolean updateMyProfile(UserProfileModel model);

    boolean changePassword(ChangePasswordModel model);

    boolean changeStatus(Long id);


}
