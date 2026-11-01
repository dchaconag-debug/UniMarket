# UniMarket

preview main  [here.](https://unimarket-main.onrender.com/)

Requires Java 21.

Run:

```bash
./mvnw spring-boot:run
```

Open http://localhost:8080.

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