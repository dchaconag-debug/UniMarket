# UniMarket

preview main  [here.](https://unimarket-main.onrender.com/)


## Cómo descargar y correr el proyecto en local

1. **Clona el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/unimarket.git](https://github.com/tu-usuario/unimarket.git)
   cd unimarket

Requires Java 21.

Run:

```bash
./mvnw spring-boot:run
```

Open http://localhost:8080.

Behind an HTTPS proxy (for example `tailscale serve`), run with the `render` profile so the app honors `X-Forwarded-*` headers:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=render
```

Without it, the app sends Google an `http://` callback and login fails with `redirect_uri_mismatch`. Register the proxy's `https://<host>/login/oauth2/code/google` as an authorized redirect URI on the OAuth client.

Test:

```bash
# Test
./mvnw test

# Build
./mvnw clean package

# Run build
java -jar target/unimarket-0.0.1-SNAPSHOT.jar
```

## Env

### Render

- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID`
- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET`
- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_SCOPE=openid,email,profile`
