package com.example.ramenddang.ramen.repository;

import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.entity.RamenPhoto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RamenPhotoRepository extends CrudRepository<RamenPhoto, Long> {
    List<RamenPhoto> findByRamen(Ramen existingRamen);
}
