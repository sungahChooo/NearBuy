package com.sungah.aug14pd.up;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProfileRepo extends CrudRepository<Profile, String>{
	 public abstract Optional<Profile> findById(String id);
}
