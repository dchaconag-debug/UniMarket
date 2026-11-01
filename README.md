# UniMarket

Requires Java 21.

Google login requires `config/application-local.yaml` with the OAuth client credentials.
Use `config/application-local.example.yaml` as the template. The local file is excluded from Git.
Register `http://localhost:8080/login/oauth2/code/google` as the redirect URI in Google Cloud.

Run:

```bash
./mvnw spring-boot:run
```

Open http://localhost:8080.

Test:

```bash
./mvnw test
```

Build:

```bash
./mvnw clean package
```

Run the built application:

```bash
java -jar target/unimarket-0.0.1-SNAPSHOT.jar
```

## Render trial on main

`render.yaml` defines a free Docker web service for `main`. PR previews are disabled.
Create a Blueprint from this repository in Render, or create a Docker web service
with the same settings. The Docker build runs the tests before packaging the app.
The `render` Spring profile uses Render's `PORT` and forwarded HTTPS headers.
Render checks `/login` to confirm the app can serve requests.

The app starts without Google credentials and shows the login page with Google
login unavailable. To enable it, set these environment variables in Render:

- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID`
- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET`
- `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_SCOPE=openid,email,profile`

In Google Cloud, register
`https://<service>.onrender.com/login/oauth2/code/google` as an authorized redirect
URI. Keep credentials in Render, outside the repository and Docker build context.

This trial uses a local SQLite database. Render's free service loses local data on
restart, redeploy, or idle shutdown. Use persistent storage before storing real data.
