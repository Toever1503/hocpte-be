package hocpte.services;

import hocpte.entities.OptionEntity;

public interface IOptionService extends IBaseService<OptionEntity, OptionEntity, OptionEntity, Long> {

    OptionEntity findByOptionKey(String optionKey) ;
}
