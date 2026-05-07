package com.gym.gymmanagementsystem.service.impl;

import com.gym.gymmanagementsystem.mapper.EquipmentMapper;
import com.gym.gymmanagementsystem.pojo.Equipment;
import com.gym.gymmanagementsystem.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public List<Equipment> findAll() {
        return equipmentMapper.findAll();
    }

    @Override
    public Boolean deleteByEquipmentId(Integer equipmentId) {
        return equipmentMapper.deleteByEquipmentId(equipmentId);
    }

    @Override
    public Boolean insertEquipment(Equipment equipment) {
        return equipmentMapper.insertEquipment(equipment);
    }

    @Override
    public Boolean updateEquipmentByEquipmentId(Equipment equipment) {
        return equipmentMapper.updateEquipmentByEquipmentId(equipment);
    }

    @Override
    public List<Equipment> selectByEquipmentId(Integer equipmentId) {
        return equipmentMapper.selectByEquipmentId(equipmentId);
    }

    @Override
    public Integer selectTotalCount() {
        return equipmentMapper.selectTotalCount();
    }
}

/*所以正确的理解是：
先声明变量（值为 null）
Spring 扫描到 @Autowired
Spring 创建/查找对应的 Bean（动态代理）
Spring 把 代理对象 赋值给变量
现在可以通过 变量 调用方法了
这就是 Spring 依赖注入（DI） 和 MyBatis 动态代理 的核心机制！*/