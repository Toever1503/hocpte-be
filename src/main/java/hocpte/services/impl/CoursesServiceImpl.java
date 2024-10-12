package hocpte.services.impl;

import hocpte.dtos.ClassDto;
import hocpte.dtos.ClassRegistrationDto;
import hocpte.dtos.CoursesDto;
import hocpte.dtos.UserMetaDto;
import hocpte.entities.ClassEntity;
import hocpte.entities.ClassRegistrationEntity;
import hocpte.entities.CoursesEntity;
import hocpte.entities.TagEntity;
import hocpte.enums.EClassRegistration;
import hocpte.enums.EClassStatus;
import hocpte.models.ClassModel;
import hocpte.models.ClassRegistrationModel;
import hocpte.models.CoursesModel;
import hocpte.repositories.IClassRegistrationRepository;
import hocpte.repositories.IClassRepository;
import hocpte.repositories.ICoursesRepository;
import hocpte.repositories.ITagRepository;
import hocpte.resources.exception.CustomHandleException;
import hocpte.services.ICoursesService;
import hocpte.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class CoursesServiceImpl implements ICoursesService {

    private final ICoursesRepository coursesRepository;
    private final IClassRepository classRepository;
    private final IClassRegistrationRepository classRegistrationRepository;

    private final ITagRepository tagRepository;

    public CoursesServiceImpl(ICoursesRepository coursesRepository, IClassRepository classRepository, IClassRegistrationRepository classRegistrationRepository, ITagRepository tagRepository) {
        this.coursesRepository = coursesRepository;
        this.classRepository = classRepository;
        this.classRegistrationRepository = classRegistrationRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public List<CoursesDto> findAll() {
        return this.coursesRepository.findAll().stream().map(CoursesDto::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<CoursesDto> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<CoursesDto> findAll(Specification<CoursesEntity> specs) {
        return null;
    }

    @Override
    public Page<CoursesDto> filter(Pageable page, Specification<CoursesEntity> specs) {

        return this.coursesRepository.findAll(specs, page).map(CoursesDto::toDto);
    }

    @Override
    public CoursesDto findById(Long id) {
        CoursesEntity coursesEntity = this.getById(id);
        return CoursesDto.toDto(coursesEntity);
    }

    @Override
    public CoursesEntity getById(Long id) {
        return this.coursesRepository.findById(id).orElseThrow(() -> new CustomHandleException(1));
    }

    @Override
    public CoursesDto add(CoursesModel model) {
        CoursesEntity coursesEntity = CoursesEntity.builder()
                .title(model.getTitle())
                .description(model.getDescription())
                .price(model.getPrice())
                .imgBanner(model.getImgBanner())
                .createdBy(SecurityUtils.getCurrentUser().getUser())
                .modifiedBy(SecurityUtils.getCurrentUser().getUser())
                .lectures(model.getLectures())
                .totalSession(model.getTotalSession())
                .build();

        if (model.getTags() != null)
            coursesEntity.setTags(
                    model.getTags()
                            .stream()
                            .map(tag -> TagEntity.builder().tagName(tag).build())
                            .collect(Collectors.toList())
            );
        coursesEntity = this.coursesRepository.saveAndFlush(coursesEntity);

        return CoursesDto.toDto(coursesEntity);
    }

    @Override
    public List<CoursesDto> add(List<CoursesModel> model) {
        return null;
    }

    @Override
    public CoursesDto update(CoursesModel model) {
        CoursesEntity original = this.getById(model.getId());

        CoursesEntity coursesEntity = CoursesEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .price(model.getPrice())
                .createdBy(original.getCreatedBy())
                .modifiedBy(SecurityUtils.getCurrentUser().getUser())
                .createdDate(original.getCreatedDate())
                .lectures(model.getLectures())
                .imgBanner(model.getImgBanner())
                .totalSession(model.getTotalSession())
                .build();

        if (original.getTags() != null && model.getTags() != null) {
            // add new tags
            List<TagEntity> newTags = model.getTags()
                    .stream()
                    .filter(tag ->
                            original.getTags()
                                    .stream()
                                    .noneMatch(tagEntity -> tagEntity.getTagName().equals(tag)))
                    .map(tag -> TagEntity.builder().tagName(tag).build())
                    .collect(Collectors.toList());
            if (newTags.size() > 0) {
                original.getTags().addAll(newTags);
                coursesEntity.setTags(original.getTags());
            }
        } else
            coursesEntity.setTags(model.getTags()
                    .stream()
                    .map(tag -> TagEntity.builder().tagName(tag).build())
                    .collect(Collectors.toList()));

        coursesEntity = this.coursesRepository.saveAndFlush(coursesEntity);

        return CoursesDto.toDto(coursesEntity);
    }

    @Override
    public boolean deleteById(Long id) {
        if (this.findById(id) == null) return false;
        this.coursesRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        if (ids == null) return false;
        this.coursesRepository.deleteAllByIdInBatch(ids);
        return true;
    }


    @Override
    public List<String> getAllCourseDate() {
        return this.coursesRepository.findAllCourseDate();
    }

    @Override
    public ClassEntity getClassById(Long id) {
        return this.classRepository.findById(id).orElseThrow(() -> new CustomHandleException(1));
    }

    @Override
    public ClassRegistrationDto registerCourse(ClassRegistrationModel model) {
        ClassRegistrationEntity entity = ClassRegistrationEntity
                .builder()
                .fullName(model.getFullName())
                .status(EClassRegistration.PENDING.name())
                .email(model.getEmail())
                .phone(model.getPhone())
                .classOfCourse(this.getClassById(model.getClassOfCourse()))
                .build();

        // check if class full
        if (entity.getClassOfCourse().getTotalStudentNow() > entity.getClassOfCourse().getMaxStudent())
            throw new CustomHandleException(2);
        else if (entity.getEmail() != null) { // check if email is registered and over 5 times
            if (this.classRegistrationRepository.countByEmail(entity.getEmail(), EClassRegistration.PENDING.name()) > 5) {
                throw new CustomHandleException(2);
            }
        } else if (entity.getPhone() != null) { // check if phone is registered and over 5 times
            if (this.classRegistrationRepository.countByPhone(entity.getPhone(), EClassRegistration.PENDING.name()) > 5) {
                throw new CustomHandleException(2);
            }
        }

        entity.getClassOfCourse().setTotalStudentNow(entity.getClassOfCourse().getTotalStudentNow() + 1);
        this.classRepository.saveAndFlush(entity.getClassOfCourse());

        entity.setPrice(entity.getClassOfCourse().getCourse().getPrice());

        if (SecurityUtils.isAuthenticated())
            entity.setCreatedBy(SecurityUtils.getCurrentUser().getUser());
        return ClassRegistrationDto.toDto(this.classRegistrationRepository.saveAndFlush(entity));
    }

    @Override
    public Page<ClassDto> filterClass(Pageable page, Specification<ClassEntity> spec) {
        return this.classRepository.findAll(spec, page).map(ClassDto::toDto);
    }

    @Override
    public ClassDto addClass(ClassModel model) {
        ClassEntity entity = ClassModel.toEntity(model);
        entity.setCourse(this.getById(model.getCourseId()));
        entity.setCreatedBy(SecurityUtils.getCurrentUser().getUser());
        entity.setModifiedBy(entity.getCreatedBy());

        Date currentDate = Calendar.getInstance().getTime();

        if (model.getStartDate().after(currentDate)) {
            entity.setStatus(EClassStatus.STARTING.name());
        } else entity.setStatus(EClassStatus.NOT_START.name());

        if (model.getEndDate().before(currentDate))
            entity.setStatus(EClassStatus.ENDED.name());

        return ClassDto.toDto(this.classRepository.saveAndFlush(entity));
    }


    @Override
    public ClassDto updateClass(ClassModel model) {
        ClassEntity original = this.getClassById(model.getId());
        ClassEntity entity = ClassModel.toEntity(model);

        if (!original.getCourse().getId().equals(model.getCourseId()))
            entity.setCourse(this.getById(model.getCourseId()));
        else
            entity.setCourse(original.getCourse());

        entity.setCreatedBy(original.getCreatedBy());
        entity.setModifiedBy(SecurityUtils.getCurrentUser().getUser());
        entity.setCreatedDate(original.getCreatedDate());

        Date currentDate = Calendar.getInstance().getTime();
        if (model.getStartDate().after(currentDate)) {
            entity.setStatus(EClassStatus.STARTING.name());
        } else entity.setStatus(EClassStatus.NOT_START.name());

        if (model.getEndDate().before(currentDate))
            entity.setStatus(EClassStatus.ENDED.name());
        return ClassDto.toDto(this.classRepository.saveAndFlush(entity));
    }

    @Override
    public boolean deleteBulkClass(List<Long> ids) {
        try {
            this.classRepository.deleteAllByIdInBatch(ids);
            return true;
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }

    @Override
    public boolean deleteBulkCourses(List<Long> ids) {
        try {
            this.classRepository.deleteAllByCourseIdIn(ids);
            this.coursesRepository.deleteAllByIdInBatch(ids);
            return true;
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }

    @Override
    public boolean deleteClass(Long id) {
        try {
            this.classRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomHandleException(1);
        }
    }


}
