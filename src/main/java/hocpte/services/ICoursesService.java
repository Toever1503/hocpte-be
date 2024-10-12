package hocpte.services;

import hocpte.dtos.ClassDto;
import hocpte.dtos.ClassRegistrationDto;
import hocpte.dtos.CoursesDto;
import hocpte.dtos.ResponseDto;
import hocpte.entities.ClassEntity;
import hocpte.entities.CoursesEntity;
import hocpte.models.ClassModel;
import hocpte.models.ClassRegistrationModel;
import hocpte.models.CoursesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ICoursesService extends IBaseService<CoursesEntity, CoursesDto, CoursesModel,Long> {
    List<String> getAllCourseDate();

    Page<ClassDto> filterClass(Pageable page, Specification<ClassEntity> filter);

    ClassDto addClass(ClassModel model);

    ClassDto updateClass(ClassModel model);

    boolean deleteClass(Long id);

    boolean deleteBulkClass(List<Long> ids);

    boolean deleteBulkCourses(List<Long> ids);

    ClassEntity getClassById(Long id);

    ClassRegistrationDto registerCourse(ClassRegistrationModel model);
}
