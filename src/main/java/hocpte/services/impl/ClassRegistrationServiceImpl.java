package hocpte.services.impl;

import hocpte.dtos.ClassRegistrationDto;
import hocpte.entities.ClassRegistrationEntity;
import hocpte.enums.EClassRegistration;
import hocpte.models.ClassRegistrationModel;
import hocpte.repositories.IClassRegistrationRepository;
import hocpte.services.ClassRegistrationService;
import hocpte.services.ICoursesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClassRegistrationServiceImpl implements ClassRegistrationService {

    private final IClassRegistrationRepository classRegistrationRepository;
    private final ICoursesService coursesService;

    public ClassRegistrationServiceImpl(IClassRegistrationRepository classRegistrationRepository, ICoursesService coursesService) {
        this.classRegistrationRepository = classRegistrationRepository;
        this.coursesService = coursesService;
    }

    @Override
    public List<ClassRegistrationDto> findAll() {
        return null;
    }

    @Override
    public Page<ClassRegistrationDto> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<ClassRegistrationDto> findAll(Specification<ClassRegistrationEntity> specs) {
        return null;
    }

    @Override
    public Page<ClassRegistrationDto> filter(Pageable page, Specification<ClassRegistrationEntity> specs) {
        return null;
    }

    @Override
    public ClassRegistrationDto findById(Long id) {
        return null;
    }

    @Override
    public ClassRegistrationEntity getById(Long id) {
        return null;
    }

    @Override
    public ClassRegistrationDto add(ClassRegistrationModel model) {
        ClassRegistrationEntity entity = ClassRegistrationModel.toEntity(model);
        entity.setStatus(EClassRegistration.PENDING.name());
        entity.setClassOfCourse(coursesService.getClassById(model.getClassOfCourse()));

        return ClassRegistrationDto.toDto(this.classRegistrationRepository.saveAndFlush(entity));
    }

    @Override
    public List<ClassRegistrationDto> add(List<ClassRegistrationModel> model) {
        return null;
    }

    @Override
    public ClassRegistrationDto update(ClassRegistrationModel model) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return false;
    }
}
