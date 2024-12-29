package com.example.demo.Controler;

import com.example.demo.Repository.Student;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@Tag(name = "admins", description = "Operations with admins")

public class AdminControler {

    private final AdminService adminService;
    private final StudentRepository studentRepository;

    public AdminControler(StudentRepository studentRepository, AdminService adminService) {
        this.studentRepository = studentRepository;
        this.adminService = adminService;
    }


    @PostMapping
    @Operation(

    		summary = "Create a student",
    		description = "Create a student with specified parameters",
    		parameters = {@Parameter(name = "student", description = "A dictionary with student`s name and group", example = "'firstname': 'Олександр', 'secondname': 'Шевченко', 'groupname': 'ІО-21'")}
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "201", description = "Created"),
    		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    		})
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        if (student.getFirstname()== null|| student.getSecondname()== null|| student.getGroupname() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        adminService.newStudent(student.getFirstname(), student.getSecondname(), student.getGroupname());
        return ResponseEntity.status(HttpStatus.CREATED).body("Student created successfully.");
    }

   
    @GetMapping
    @Operation(

    		summary = "Get Students",
    		description = "Get a list with all Students"
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "200", description = "Success")
    		})
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = studentRepository.findList();
        return ResponseEntity.ok(studentList);
    }

   
    @GetMapping("/{id}")
    @Operation(

    		summary = "Get Student By Id",
    		description = "Get Student by specific identifier",
    		parameters = {@Parameter(name = "id", description = "Student Id", example = "8")}
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "200", description = "Success"),
    		@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    		})
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int studId) {
        Student student = studentRepository.findStudenById(studId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(student);
    }

    
    @PutMapping("/{id}")
    @Operation(

    		summary = "Update Student By Id",
    		description = "Update Student by specific identifier",
    		parameters = {@Parameter(name = "studId", description = "Student Id", example = "8"),
    					  @Parameter(name = "updatedStudent", description = "A dictionary with student`s name and group", example = "'firstname': 'Олександр', 'secondname': 'Шевченко', 'groupname': 'ІО-21'")
    						}
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "200", description = "Success"),
    		@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    		})
    public ResponseEntity<String> updateStudent(@PathVariable("id") int studId, @RequestBody Student updatedStudent) {
    	
        Student student = studentRepository.findStudenById(studId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        if (updatedStudent.getFirstname()== null|| updatedStudent.getSecondname()== null|| updatedStudent.getGroupname() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        adminService.editStudent(studId, updatedStudent.getFirstname(), updatedStudent.getSecondname(), updatedStudent.getGroupname());
        
        return ResponseEntity.ok("Student updated successfully.");
    }

   
    @DeleteMapping("/{id}")
    @Operation(

    		summary = "Delete Student By Id",
    		description = "Delete Student by specific identifier",
    		parameters = {@Parameter(name = "id", description = "Student Id", example = "8")}
    		)
    		@ApiResponses({
    		@ApiResponse(responseCode = "200", description = "Success"),
    		@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    		})
    public ResponseEntity<String> deleteStudent(@PathVariable("id") int studId) {
        Student student = studentRepository.findStudenById(studId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        adminService.removeStudent(studId);
        return ResponseEntity.ok("Student deleted successfully.");
    }
}