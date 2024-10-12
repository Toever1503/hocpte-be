package hocpte.services;

import hocpte.dtos.ClassRegistrationDto;
import hocpte.entities.ClassRegistrationEntity;
import hocpte.models.ClassRegistrationModel;
import hocpte.services.IBaseService;

public interface ClassRegistrationService extends IBaseService<ClassRegistrationEntity, ClassRegistrationDto, ClassRegistrationModel, Long> {
}
