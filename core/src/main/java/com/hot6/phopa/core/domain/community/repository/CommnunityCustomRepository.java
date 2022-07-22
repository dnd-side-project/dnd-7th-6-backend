package com.hot6.phopa.core.domain.community.repository;

import com.hot6.phopa.core.domain.community.model.entity.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommnunityCustomRepository {
    List<PostEntity> findAll();
}
