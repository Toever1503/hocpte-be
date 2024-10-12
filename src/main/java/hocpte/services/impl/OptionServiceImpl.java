package hocpte.services.impl;

import hocpte.entities.OptionEntity;
import hocpte.repositories.IOptionRepository;
import hocpte.resources.exception.CustomHandleException;
import hocpte.services.IOptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements IOptionService {

    private final IOptionRepository optionRepository;

    public OptionServiceImpl(IOptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }


    @Override
    public List<OptionEntity> findAll() {
        return null;
    }

    @Override
    public Page<OptionEntity> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<OptionEntity> findAll(Specification<OptionEntity> specs) {
        return null;
    }

    @Override
    public Page<OptionEntity> filter(Pageable page, Specification<OptionEntity> specs) {
        return null;
    }

    @Override
    public OptionEntity findById(Long id) {
        return this.getById(id);
    }

    @Override
    public OptionEntity getById(Long id) {
        return this.optionRepository.findById(id).orElseThrow(() -> new CustomHandleException(1));
    }

    @Override
    public OptionEntity add(OptionEntity model) {
        String newVal = model.getOptionValue();
        model.setOptionKey(model.getOptionKey().replace(" ", ""));
        model = this.optionRepository.findByOptionKey(model.getOptionKey()).orElse(model);
        model.setOptionValue(newVal);
        return this.optionRepository.saveAndFlush(model);
    }

    @Override
    public List<OptionEntity> add(List<OptionEntity> model) {
        return null;
    }

    @Override
    public OptionEntity update(OptionEntity model) {
        return this.add(model);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            this.optionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        try {
            this.optionRepository.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public OptionEntity findByOptionKey(String optionKey) {
        return this.optionRepository.findByOptionKey(optionKey).orElseThrow(() -> new CustomHandleException(1));
    }
}
