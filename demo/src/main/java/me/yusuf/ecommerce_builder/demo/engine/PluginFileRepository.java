package me.yusuf.ecommerce_builder.demo.engine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PluginFileRepository extends CrudRepository<PluginClassFile, PluginClassFile.Id>,PagingAndSortingRepository<PluginClassFile, PluginClassFile.Id> {

}
