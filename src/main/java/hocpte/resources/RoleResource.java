package hocpte.resources;

import hocpte.configs.FrontendConfiguration;
import hocpte.dtos.ResponseDto;
import hocpte.repositories.IRoleRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "roles")
public class RoleResource {
    private final IRoleRepository roleRepository;

    public RoleResource(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseDto getAll() {
        return ResponseDto.of(this.roleRepository.findAll());
    }

}
