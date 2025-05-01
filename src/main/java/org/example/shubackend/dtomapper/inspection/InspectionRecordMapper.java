package org.example.shubackend.dtomapper.inspection;

import org.example.shubackend.dto.inspection.InspectionRecordDTO;
import org.example.shubackend.entity.work.inspection.InspectionRecord;
import org.example.shubackend.repository.DeviceRepository;
import org.example.shubackend.repository.InspectionPlanRepository;
import org.example.shubackend.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class InspectionRecordMapper {

    @Autowired
    protected InspectionPlanRepository planRepo;
    @Autowired
    protected DeviceRepository deviceRepo;
    @Autowired
    protected UserRepository userRepo;

    /* ---------- DTO -> Entity (全部用 getReferenceById) ---------- */
    public InspectionRecord toEntity(InspectionRecordDTO dto) {
        if (dto == null) {
            return null;
        }
        InspectionRecord entity = new InspectionRecord();
        // 如果 BaseEntity 有 setId 方法
        entity.setId(dto.id());
        // 关联引用
        entity.setPlan(planRepo.getReferenceById(dto.plan().id()));
        entity.setDevice(deviceRepo.getReferenceById(dto.device().id()));
        entity.setInspector(userRepo.getReferenceById(dto.inspector().id()));
        // 其余字段
        entity.setCheckItems(dto.checkItems());
        entity.setStatus(dto.status());
        entity.setIssuesFound(dto.issuesFound());
        entity.setInspectionTime(dto.inspectionTime());
        return entity;
    }

    /* ---------- Entity -> DTO（MapStruct 根据 record constructor 自动生成） ---------- */
    @Mapping(target = "plan", source = "plan")
    @Mapping(target = "device", source = "device")
    @Mapping(target = "inspector", source = "inspector")
    public abstract InspectionRecordDTO toDto(InspectionRecord entity);
}
