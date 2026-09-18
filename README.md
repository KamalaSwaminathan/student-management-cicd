# Student Management – End-to-End CI/CD Pipeline

A small Spring Boot web application (add & view student records) with a full
Git → Maven → Docker → Jenkins pipeline, built for the CI/CD assignment.

## What's inside

- `src/main/java` – Spring Boot app: `Student` model, `StudentService`
  (in-memory add/view logic), `StudentController` (REST API), and the
  `index.html` front end.
- `src/test/java` – JUnit 5 unit tests for `StudentService`.
- `pom.xml` – Maven build (compile, test, package as an executable JAR).
- `Dockerfile` – multi-stage build that packages the JAR into a slim runtime image.
- `Jenkinsfile` – declarative pipeline: Checkout → Build & Test → Package →
  Build Docker Image → Deploy Container.

## 1. Run it locally first (sanity check)

```bash
cd student-management-cicd
mvn clean test          # runs the unit tests
mvn spring-boot:run      # starts the app on http://localhost:8080
```

Open http://localhost:8080 in a browser — you should see the Student
Management page. Add a student and confirm it shows up in the table.
Stop it with Ctrl+C.

**Screenshot #1 (Maven build/test):** the terminal output of `mvn clean test`
showing `BUILD SUCCESS` and the tests that ran.

## 2. Git

```bash
git log --oneline --graph      # shows the commit history already in this project
git remote add origin <your-empty-GitHub-repo-URL>
git push -u origin main
```

Create an empty repository on GitHub first (no README/license, so there's no
merge conflict), then run the two commands above with its URL.

**Screenshot #2 (Git repo & commits):** the GitHub repo page showing the
commit history, and/or `git log` in your terminal.

## 3. Docker

```bash
docker build -t student-management .
docker run -d --name student-management-app -p 8080:8080 student-management
docker ps
```

Then open http://localhost:8080 again — this time it's being served from
inside the container.

**Screenshot #3 (Docker):** `docker images` (showing the built image),
`docker ps` (showing the running container), and the browser at
http://localhost:8080.

Clean up when done: `docker rm -f student-management-app`

## 4. Jenkins

The easiest path for a laptop is to run Jenkins itself in Docker, mounting
the host's Docker socket so Jenkins can build/run images on your machine:

```bash
docker run -d --name jenkins \
  -p 8081:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v $(which docker):/usr/bin/docker \
  jenkins/jenkins:lts
```

1. Open http://localhost:8081, unlock Jenkins with the initial admin
   password (`docker logs jenkins` prints it), install the **suggested
   plugins** (this includes Git and Pipeline).
2. Create a **New Item → Pipeline**, name it `student-management-pipeline`.
3. Under **Pipeline**, choose **Pipeline script from SCM** → SCM: **Git** →
   paste your GitHub repo URL → Script Path: `Jenkinsfile`.
4. Click **Build Now**.

**Screenshot #4 (Jenkins pipeline):** the Jenkins **Stage View**, showing
every stage (Checkout, Build & Test, Package, Build Docker Image, Deploy
Container) green, plus the console output of a successful run.

> If `docker` isn't found inside the Jenkins container during the pipeline,
> it's because Docker isn't mounted in — double-check the two `-v` flags
> above that bind-mount the host's Docker socket and CLI binary.

## Deliverables checklist

- [x] Git repository with meaningful commit history (see `git log`)
- [x] Complete source code
- [x] `pom.xml`
- [x] Unit test (`StudentServiceTest`)
- [x] `Dockerfile`
- [x] `Jenkinsfile`
- [ ] Screenshot: Git repository and commits
- [ ] Screenshot: successful Maven build/test
- [ ] Screenshot: Docker image + running container
- [ ] Screenshot: successful Jenkins pipeline (all stages green)
