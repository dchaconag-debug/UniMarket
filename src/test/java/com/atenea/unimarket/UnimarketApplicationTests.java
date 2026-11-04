package com.atenea.unimarket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:sqlite:target/unimarket-test.db")
class UnimarketApplicationTests {

	@Autowired
	UsuarioRepository usuarios;

	@Test
	void contextLoads() {
		assertThat(usuarios.existsByGoogleSub("demo-ana")).isFalse();
	}

}
