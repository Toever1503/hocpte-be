package hocpte.services.impl;

import hocpte.configs.jwt.JwtLoginResponse;
import hocpte.configs.jwt.JwtProvider;
import hocpte.configs.jwt.JwtUserLoginModel;
import hocpte.dtos.RoleDto;
import hocpte.dtos.UserProfileDto;
import hocpte.entities.RoleEntity;
import hocpte.entities.UserEntity;
import hocpte.models.*;
import hocpte.repositories.IRoleRepository;
import hocpte.repositories.IUserRepository;
import hocpte.resources.exception.CustomHandleException;
import hocpte.services.IUserService;
import hocpte.specifications.UserSpecification;
import hocpte.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

        List<RoleEntity> defaultRoles = new ArrayList<>();
        defaultRoles.add(RoleEntity.builder()
                .id(1L)
                .roleName(RoleEntity.ADMIN)
                .build());
        defaultRoles.add(RoleEntity.builder()
                .id(2L)
                .roleName(RoleEntity.EMPLOYEE)
                .build());
        defaultRoles.add(RoleEntity.builder()
                .id(3L)
                .roleName(RoleEntity.TEACHER)
                .build());
        defaultRoles.add(RoleEntity.builder()
                .id(4L)
                .roleName(RoleEntity.USER)
                .build());
        this.roleRepository.saveAllAndFlush(defaultRoles);

        this.userRepository.saveAndFlush(
                UserEntity.builder()
                        .id(1L)
                        .userName("admin")
                        .password(this.passwordEncoder.encode("123456"))
                        .fullName("admin")
                        .sex("male")
                        .email("admin@admin.com")
                        .status(true)
                        .createdDate(Calendar.getInstance().getTime())
                        .modifiedDate(Calendar.getInstance().getTime())
                        .roleEntity(defaultRoles.get(0))
                        .build());

    }

    @Override
    public List<UserProfileDto> findAll() {
        return this.userRepository.findAll().stream().map(userEntity -> {
                    UserProfileDto userProfileDto = new UserProfileDto();
                    userProfileDto.setUserName(userEntity.getUserName());
                    userProfileDto.setId(userEntity.getId());
                    userProfileDto.setAvatar(userEntity.getAvatar());
                    userProfileDto.setEmail(userEntity.getEmail());
                    userProfileDto.setAddress(userEntity.getAddress());
                    userProfileDto.setPhone(userEntity.getPhone());
                    userProfileDto.setSex(userEntity.getSex());
                    userProfileDto.setFullName(userEntity.getFullName());
                    userProfileDto.setBirthDate(userEntity.getBirthDate());
                    userProfileDto.setStatus(userEntity.getStatus());
                    RoleDto roleDto = new RoleDto();
                    roleDto.setId(userEntity.getRoleEntity().getId());
                    roleDto.setRoleName(userEntity.getRoleEntity().getRoleName());
                    userProfileDto.setModifiedDate(userEntity.getModifiedDate());
                    userProfileDto.setCreatedDate(userEntity.getCreatedDate());
                    return userProfileDto;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public Page<UserProfileDto> findAll(Pageable page) {
        return this.userRepository.findAll(Specification.not(UserSpecification.byId(SecurityUtils.getCurrentUserId())), page).map(UserProfileDto::toDto);
    }

    @Override
    public List<UserProfileDto> findAll(Specification<UserEntity> specs) {
        return null;
    }

    @Override
    public Page<UserProfileDto> filter(Pageable page, Specification<UserEntity> specs) {
        return this.userRepository.findAll(specs, page).map(UserProfileDto::toDto);
    }

    public Page<UserProfileDto> filter(Pageable page, UserFilterModel filterModel) {
        return userRepository.findAll((Specification<UserEntity>) (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    if (filterModel.getUserName() != null && !"".equalsIgnoreCase(filterModel.getUserName())) {
                        predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("userName"), "%" + filterModel.getUserName() + "%")));
                    }
                    if (filterModel.getFullName() != null && !"".equalsIgnoreCase(filterModel.getFullName())) {
                        predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("fullName"), "%" + filterModel.getFullName() + "%")));
                    }
                    if (filterModel.getSex() != null && !"".equalsIgnoreCase(filterModel.getSex())) {
                        predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("sex"), "%" + filterModel.getSex() + "%")));
                    }
                    if (filterModel.getEmail() != null && !"".equalsIgnoreCase(filterModel.getEmail())) {
                        predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("email"), "%" + filterModel.getEmail() + "%")));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }, page
        ).map(userEntity -> {
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setUserName(userEntity.getUserName());
            userProfileDto.setId(userEntity.getId());
            userProfileDto.setAvatar(userEntity.getAvatar());
            userProfileDto.setEmail(userEntity.getEmail());
            userProfileDto.setAddress(userEntity.getAddress());
            userProfileDto.setPhone(userEntity.getPhone());
            userProfileDto.setSex(userEntity.getSex());
            userProfileDto.setFullName(userEntity.getFullName());
            userProfileDto.setBirthDate(userEntity.getBirthDate());
            userProfileDto.setStatus(userEntity.getStatus());
            RoleDto roleDto = new RoleDto();
            roleDto.setId(userEntity.getRoleEntity().getId());
            roleDto.setRoleName(userEntity.getRoleEntity().getRoleName());
            userProfileDto.setModifiedDate(userEntity.getModifiedDate());
            userProfileDto.setCreatedDate(userEntity.getCreatedDate());
            return userProfileDto;
        });

    }

    @Override
    public UserProfileDto findById(Long id) {
        return null;
    }

    @Override
    public UserEntity getById(Long id) {
        return null;
    }

    @Override
    public UserProfileDto add(UserModel model) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(model.getId());
        userEntity.setUserName(model.getUserName());
        userEntity.setEmail(model.getEmail());
        userEntity.setSex(model.getSex());
        userEntity.setFullName(model.getFullName());
        userEntity.setAddress(model.getAddress());
        userEntity.setPassword(this.passwordEncoder.encode(model.getPassword()));
        userEntity.setBirthDate(model.getBirthDate());

        RoleEntity roleEntity = this.roleRepository.findById(model.getRole()).get();
        userEntity.setRoleEntity(roleEntity);
        userEntity.setPhone(model.getPhone());
        userRepository.save(userEntity);

        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setUserName(userEntity.getUserName());
        userProfileDto.setId(userEntity.getId());
        userProfileDto.setAvatar(userEntity.getAvatar());
        userProfileDto.setEmail(userEntity.getEmail());
        userProfileDto.setAddress(userEntity.getAddress());
        userProfileDto.setPhone(userEntity.getPhone());
        userProfileDto.setSex(userEntity.getSex());
        userProfileDto.setFullName(userEntity.getFullName());
        userProfileDto.setBirthDate(userEntity.getBirthDate());
        userProfileDto.setStatus(userEntity.getStatus());
        RoleDto roleDto = new RoleDto();
        roleDto.setId(userEntity.getRoleEntity().getId());
        roleDto.setRoleName(userEntity.getRoleEntity().getRoleName());
        userProfileDto.setRole(roleDto);

        userProfileDto.setModifiedDate(userEntity.getModifiedDate());
        userProfileDto.setCreatedDate(userEntity.getCreatedDate());
        return userProfileDto;
    }

    @Override
    public List<UserProfileDto> add(List<UserModel> model) {
        return null;
    }

    @Override
    public UserProfileDto add(CustomUserRegister customUserRegister) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(customUserRegister.getUserName());
        userEntity.setFullName(customUserRegister.getFullName());
        userEntity.setPassword(this.passwordEncoder.encode(customUserRegister.getPassword()));
        userEntity.setEmail(customUserRegister.getEmail());

        userRepository.save(userEntity);
        return UserProfileDto.builder()
                .userName(userEntity.getUserName())
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .build();
    }

    @Override
    public UserProfileDto update(UserModel model) {

        SecurityUtils.getCurrentUser().getUser().setId(model.getId());

        UserEntity userEntity = this.userRepository.findById(model.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setEmail(model.getEmail());
        userEntity.setSex(model.getSex());
        userEntity.setFullName(model.getFullName());
        userEntity.setAddress(model.getAddress());

        if (model.getPassword() != null)
            userEntity.setPassword(this.passwordEncoder.encode(model.getPassword()));

        userEntity.setBirthDate(model.getBirthDate());

        if (model.getRole() != null) {
            if (!model.getRole().equals(userEntity.getRoleEntity().getId())) {
                RoleEntity roleEntity = this.roleRepository.findById(model.getRole()).get();
                userEntity.setRoleEntity(roleEntity);
            }
        }


        userEntity.setPhone(model.getPhone());
        userRepository.save(userEntity);

        return UserProfileDto.builder()
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .sex(userEntity.getSex())
                .fullName(userEntity.getFullName())
                .address(userEntity.getAddress())
                .birthDate(userEntity.getBirthDate())
                .phone(userEntity.getPhone())
                .role(RoleDto.builder()
                        .id(userEntity.getRoleEntity().getId())
                        .roleName(userEntity.getRoleEntity().getRoleName())
                        .build())
                .build();
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            this.userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        try {
            this.userRepository.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JwtLoginResponse logIn(JwtUserLoginModel userLogin) {
        UserEntity user = this.findByUsername(userLogin.getUsername());
        UserDetails userDetail = new CustomUserDetail(user);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetail, userLogin.getPassword(), userDetail.getAuthorities()));
        } catch (Exception e) {
            throw e;
        }

        long timeValid = userLogin.isRemember() ? 86400 * 7 : 1800L;
        return JwtLoginResponse.builder().id(user.getId()).token(this.jwtProvider.generateToken(userDetail.getUsername(), timeValid)).type("Bearer").roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).timeValid(timeValid).build();
    }

    @Override
    public boolean tokenFilter(String token, HttpServletRequest req, HttpServletResponse res) {
        try {
            String username = this.jwtProvider.getUsernameFromToken(token);
            CustomUserDetail userDetail = new CustomUserDetail(this.findByUsername(username));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            req.getSession().setAttribute("object", usernamePasswordAuthenticationToken.getPrincipal());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeMyAvatar(MultipartFile file) {
        UserEntity userEntity = this.getById(SecurityUtils.getCurrentUserId());
        if (userEntity.getId().equals(1L))
            throw new CustomHandleException(19);
        userEntity.setAvatar(null);
        this.userRepository.saveAndFlush(userEntity);
        return true;
    }

    @Override
    public boolean updateMyProfile(UserProfileModel model) {
        UserEntity userEntity = this.getById(SecurityUtils.getCurrentUserId());
        if (userEntity.getId().equals(1L))
            throw new CustomHandleException(19);
        this.checkUserInfoDuplicate(userEntity, model.getEmail(), model.getPhone());
        userEntity.setFullName(model.getFullName());
        userEntity.setBirthDate(model.getBirthDate());
        userEntity.setSex(model.getSex());
        userEntity.setAddress(model.getAddress());
        userEntity.setPhone(model.getPhone());
        userEntity.setEmail(model.getEmail());
        this.userRepository.saveAndFlush(userEntity);
        return true;
    }

    @Override
    public boolean changePassword(ChangePasswordModel model) {
        logger.info("{} is changing password", SecurityUtils.getCurrentUserLogin());
        UserEntity userEntity = SecurityUtils.getCurrentUser().getUser();
        if (userEntity.getId().equals(1L))
            throw new CustomHandleException(19);

        if (!BCrypt.checkpw(model.getCurrentPasswd(), userEntity.getPassword()))
            throw new CustomHandleException(20);

        userEntity.setPassword(this.passwordEncoder.encode(model.getNewPasswd().trim()));
        this.userRepository.saveAndFlush(userEntity);
        return true;
    }

    @Override
    public boolean changeStatus(Long id) {
        logger.info("{} is changing status", SecurityUtils.getCurrentUserLogin());
        UserEntity userEntity = this.getById(id);
        if (userEntity.getId().equals(1L))
            throw new CustomHandleException(19);
        userEntity.setStatus(!userEntity.getStatus());
        this.userRepository.saveAndFlush(userEntity);
        return true;
    }

    public UserEntity findByUsername(String userName) {
        return this.userRepository.findUserEntityByUserNameOrEmail(userName, userName).orElseThrow(() -> new CustomHandleException(11));
    }

    private void checkUserInfoDuplicate(UserEntity userEntity, String email, String phone) {
        // check user has existed if user update their email
        if (email != null)
            if (!email.equals(userEntity.getEmail())) {
                UserEntity checkUser = this.userRepository.findByEmail(phone);
                if (checkUser != null && !checkUser.getId().equals(userEntity.getId()))
                    throw new CustomHandleException(12);
            }

        // check user has existed if user update their phone
        if (phone != null)
            if (!phone.equals(userEntity.getPhone())) {
                UserEntity checkUser = this.userRepository.findByPhone(phone);
                if (checkUser != null && !checkUser.getId().equals(userEntity.getId()))
                    throw new CustomHandleException(14);
            }

    }
}
