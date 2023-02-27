package com.eoemusic.eoebackend.repository;

import com.eoemusic.eoebackend.entity.AssetConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 27/02/23 3:37 PM
 */

public interface AssetConfigRepository extends JpaRepository<AssetConfig, Integer> {

  AssetConfig findByName(String name);
}
