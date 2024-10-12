package hocpte.resources;

import hocpte.configs.FrontendConfiguration;
import hocpte.configs.jwt.JwtUserLoginModel;
import hocpte.dtos.ResponseDto;
import hocpte.entities.UserEntity;
import hocpte.entities.UserEntity_;
import hocpte.models.ChangePasswordModel;
import hocpte.models.CustomUserRegister;
import hocpte.models.UserFilterModel;
import hocpte.models.UserModel;
import hocpte.services.IUserService;
import hocpte.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "users")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IUserService userService;

    public UserResource(IUserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseDto getAllUsers(Pageable page) {
        return ResponseDto.of(userService.findAll(page));
    }

    @PostMapping(value = "/add")
    public ResponseDto addUser(@RequestBody UserModel userModel) {
        userModel.setId(null);
        return ResponseDto.of(this.userService.add(userModel));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseDto updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {
        userModel.setId(id);
        return ResponseDto.of(this.userService.update(userModel));
    }

    //đăng nhập
    @PostMapping(value = "/login")
    public ResponseDto login(@RequestBody JwtUserLoginModel jwtUserLoginModel) {
        return ResponseDto.of(this.userService.logIn(jwtUserLoginModel));
    }

    //xóa đối tượng
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable long id) {
        this.userService.deleteById(id);
    }

    //khóa / mở khóa tài khoản
    @PatchMapping(value = "/change-status")
    public void changeStatus(@PathVariable long id) {
        this.userService.changeStatus(id);
    }

    // thay đổi mật khẩu
    @PatchMapping(value = "/change-password")
    public void changPassword(@Valid @RequestBody ChangePasswordModel model) {
        this.userService.changePassword(model);
    }

    //API đăng kí người dùng
    @PostMapping(value = "/register")
    public ResponseDto register(@RequestBody CustomUserRegister customUserRegister) {
        return ResponseDto.of(this.userService.add(customUserRegister));
    }

    //API filter người dùng
    @PostMapping("/filter")
    public ResponseDto filter(@RequestBody UserFilterModel filterModel, Pageable page) {
        Specification<UserEntity> specification = (root, query, criteriaBuilder) -> {
//                List<Predicate> predicates = new ArrayList<>();
//
//                if (filterModel.getUserName()!=null && !"".equalsIgnoreCase(filterModel.getUserName())){
//                    predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("userName"),"%" + filterModel.getUserName() + "%")));
//                }
//                if (filterModel.getFullName()!=null && !"".equalsIgnoreCase(filterModel.getFullName())){
//                    predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("fullName"),"%" + filterModel.getFullName() + "%")));
//                }
//                if (filterModel.getSex()!=null && !"".equalsIgnoreCase(filterModel.getSex())){
//                    predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("sex"),"%" + filterModel.getSex() + "%")));
//                }
//                if (filterModel.getEmail()!=null && !"".equalsIgnoreCase(filterModel.getEmail())){
//                    predicates.add((Predicate) criteriaBuilder.and(criteriaBuilder.like(root.get("email"),"%" + filterModel.getEmail() + "%")));
//                }
//                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get(UserEntity_.USER_NAME), "%" + filterModel.getUserName() + "%"),
                    criteriaBuilder.like(root.get(UserEntity_.FULL_NAME), "%" + filterModel.getFullName() + "%"),
                    criteriaBuilder.like(root.get(UserEntity_.EMAIL), "%" + filterModel.getEmail() + "%"),
                    criteriaBuilder.equal(root.get(UserEntity_.ID), SecurityUtils.getCurrentUserId()).not()
            );
        };

        return ResponseDto.of(this.userService.filter(page, Specification.where(specification)));

    }

    @PostMapping(value = "/getFilter")
    public ResponseDto getAll() {
        return ResponseDto.of(userService.findAll());
    }
}
