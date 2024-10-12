package hocpte.resources;

import hocpte.configs.FrontendConfiguration;
import hocpte.dtos.ResponseDto;
import hocpte.entities.OptionEntity;
import hocpte.services.IOptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(FrontendConfiguration.PREFIX_API + "options")
public class OptionResource {

    private final IOptionService optionService;

    public OptionResource(IOptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("{id}")
    public ResponseDto findOptionById(@PathVariable Long id) {
        return ResponseDto.of(this.optionService.findById(id));
    }

    @GetMapping("find-by-key/{key}")
    public ResponseDto findOptionById(@PathVariable String key) {
        return ResponseDto.of(this.optionService.findByOptionKey(key));
    }

    @PostMapping
    public ResponseDto saveOption(@RequestBody OptionEntity model) {
        return ResponseDto.of(this.optionService.add(model));
    }

    @DeleteMapping("{id}")
    public ResponseDto deleteOption(@PathVariable Long id) {
        return ResponseDto.of(this.optionService.deleteById(id));
    }
}
