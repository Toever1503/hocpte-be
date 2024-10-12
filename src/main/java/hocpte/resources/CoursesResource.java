package hocpte.resources;


import hocpte.configs.FrontendConfiguration;
import hocpte.dtos.ResponseDto;
import hocpte.models.*;
import hocpte.services.ICoursesService;
import hocpte.specifications.ClassSpecification;
import hocpte.specifications.CourseSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "courses")
public class CoursesResource {

    private final ICoursesService coursesService;

    public CoursesResource(ICoursesService coursesService) {
        this.coursesService = coursesService;
    }


    @GetMapping("all")
    public ResponseDto getAllCourses(){
        return ResponseDto.of(this.coursesService.findAll());
    }
    @GetMapping("{id}")
    public ResponseDto findById(@PathVariable Long id) {
        return ResponseDto.of(this.coursesService.findById(id));
    }

    @PostMapping("filter")
    public ResponseDto filterCourse(Pageable page, @RequestBody CourseFilterModel model) {
        return ResponseDto.of(this.coursesService.filter(page, CourseSpecification.filter(model)));
    }

    @GetMapping("get-all-course-date")
    public ResponseDto getAllCourseDate() {
        return ResponseDto.of(this.coursesService.getAllCourseDate());
    }

    @PostMapping
    public ResponseDto addCourses(@Valid @RequestBody CoursesModel model) {
        return ResponseDto.of(this.coursesService.add(model));
    }

    @PutMapping("{id}")
    public ResponseDto updateCourses(@PathVariable(value = "id") Long id, @RequestBody CoursesModel model) {
        model.setId(id);
        return ResponseDto.of(this.coursesService.update(model));
    }

    @DeleteMapping("bulk")
    public void deleteCourses(@RequestParam List<Long> ids) {
        this.coursesService.deleteBulkCourses(ids);
    }

    @PostMapping("classes/filter")
    public ResponseDto filterClass(Pageable page, @RequestBody ClassFilterModel model) {
        return ResponseDto.of(this.coursesService.filterClass(page, ClassSpecification.filter(model)));
    }

    @PostMapping("classes")
    public ResponseDto addClass(@Valid @RequestBody ClassModel model) {
        model.setId(null);
        return ResponseDto.of(this.coursesService.addClass(model));
    }

    @PutMapping("classes/{id}")
    public ResponseDto updateClass(@PathVariable Long id, @Valid @RequestBody ClassModel model) {
        model.setId(id);
        return ResponseDto.of(this.coursesService.updateClass(model));
    }

    @DeleteMapping("classes/{id}")
    public ResponseDto deleteClass(@PathVariable Long id) {
        return ResponseDto.of(this.coursesService.deleteClass(id));
    }

    @DeleteMapping("classes/bulk")
    public ResponseDto deleteClass(@RequestParam List<Long> ids) {
        return ResponseDto.of(this.coursesService.deleteBulkClass(ids));
    }


    @PostMapping("classes/register")
    public ResponseDto registerCourse(@Valid @RequestBody ClassRegistrationModel model){
        return ResponseDto.of(this.coursesService.registerCourse(model));
    }

}
