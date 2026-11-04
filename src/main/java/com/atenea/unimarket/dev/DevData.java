package com.atenea.unimarket.dev;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.atenea.unimarket.datos.repositorio.UsuarioRepository;

@Component
@Profile("dev")
public class DevData implements ApplicationRunner {

    private final DataSource dataSource;
    private final UsuarioRepository usuarios;

    public DevData(DataSource dataSource, UsuarioRepository usuarios) {
        this.dataSource = dataSource;
        this.usuarios = usuarios;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!usuarios.existsByGoogleSub("demo-ana")) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                    new ClassPathResource("db/dev/data.sql"));
            populator.setSqlScriptEncoding("UTF-8");
            populator.execute(dataSource);
        }
    }
}
