# ==========================================
# STAGE 1 — BUILD
# ==========================================

FROM tomcat:10.1.57-jdk21-temurin-noble AS builder

ENV ANT_VERSION=1.10.14
ENV ANT_HOME=/opt/apache-ant-${ANT_VERSION}
ENV PATH=${ANT_HOME}/bin:${PATH}

# Instala o wget para baixar o Apache Ant
RUN apt-get update && \
    apt-get install -y wget && \
    rm -rf /var/lib/apt/lists/*

# Baixa e instala o Apache Ant
RUN wget -q https://archive.apache.org/dist/ant/binaries/apache-ant-${ANT_VERSION}-bin.tar.gz && \
    tar -xzf apache-ant-${ANT_VERSION}-bin.tar.gz -C /opt/ && \
    rm apache-ant-${ANT_VERSION}-bin.tar.gz

# Pasta onde o projeto será colocado
WORKDIR /workspace

# Copia o projeto para dentro do container
COPY . .

COPY docker/copylibs/org-netbeans-modules-java-j2seproject-copylibstask.jar /opt/copylibs/
COPY docker/libs/postgresql-42.7.13.jar /opt/libs/

COPY docker/copylibs/org-netbeans-modules-java-j2seproject-copylibstask.jar /opt/copylibs/

# Constrói o WAR usando o target "dist" do Ant
RUN ant \
    -Dj2ee.server.home=/usr/local/tomcat \
    -Dlibs.CopyLibs.classpath=/opt/copylibs/org-netbeans-modules-java-j2seproject-copylibstask.jar \
    -Dfile.reference.postgresql-42.7.13.jar=/opt/libs/postgresql-42.7.13.jar \
    dist


# ==========================================
# STAGE 2 — RUNTIME
# ==========================================

FROM tomcat:10.1.57-jdk21-temurin-noble

# Copia o WAR produzido no Stage 1 para o Tomcat
COPY --from=builder /workspace/dist/EntreEntrelinhasWeb.war /usr/local/tomcat/webapps/

