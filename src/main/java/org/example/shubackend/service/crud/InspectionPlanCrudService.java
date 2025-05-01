package org.example.shubackend.service.crud;

import jakarta.transaction.Transactional;
import org.example.shubackend.dto.inspection.InspectionPlanDTO;
import org.example.shubackend.entity.User;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.entity.work.inspection.InspectionPlan;
import org.example.shubackend.repository.DeviceRepository;
import org.example.shubackend.repository.InspectionPlanRepository;
import org.example.shubackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class InspectionPlanCrudService extends GenericCrudService<InspectionPlan, Integer> {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public InspectionPlanCrudService(InspectionPlanRepository r, UserRepository userRepository, DeviceRepository deviceRepository) {
        super(r);
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    /* ---------- 新增 ---------- */
    @Transactional
    public InspectionPlan createByDto(InspectionPlanDTO dto) {
        InspectionPlan plan = new InspectionPlan();
        fillEntity(plan, dto);
        return repo.save(plan);
    }

    /* ---------- 修改 ---------- */
    @Transactional
    public InspectionPlan updateByDto(Integer id, InspectionPlanDTO dto) {
        InspectionPlan plan = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("计划不存在"));
        fillEntity(plan, dto);
        return repo.save(plan);
    }

    /* ---------- 公共装配逻辑 ---------- */
    private void fillEntity(InspectionPlan plan, InspectionPlanDTO dto) {
        // 1. 关联对象用 JPA 代理（懒加载，不发 SELECT）
        Device dev = deviceRepository.getReferenceById(dto.device().id());
        User usr = userRepository.getReferenceById(dto.assignee().id());

        plan.setDevice(dev);
        plan.setAssignee(usr);

        // 2. 简单字段直接 set
        plan.setFrequency(dto.frequency());
        plan.setNextDueDate(dto.nextDueDate());
        plan.setStatus(dto.status());
    }
}
