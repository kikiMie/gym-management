package com.gym.gymmanagementsystem.mapper;

import com.gym.gymmanagementsystem.pojo.Equipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EquipmentMapper {
    List<Equipment> findAll();

    Boolean deleteByEquipmentId(Integer equipmentId);

    Boolean insertEquipment(Equipment equipment);

    Boolean updateEquipmentByEquipmentId(Equipment equipment);

    List<Equipment> selectByEquipmentId(Integer equipmentId);

    Integer selectTotalCount();
}
