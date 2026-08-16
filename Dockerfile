# ==========================================
# STAGE 1 — BUILD
# ==========================================

FROM eclipse-temurin:21-jdk AS builder

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

# Constrói o WAR usando o target "dist" do Ant
RUN ant dist


# ==========================================
# STAGE 2 — RUNTIME
# ==========================================

FROM tomcat:10.1.57-jdk21-temurin-noble

# Copia o WAR produzido no Stage 1 para o Tomcat
COPY --from=builder /workspace/dist/EntreEntrelinhasWeb.war /usr/local/tomcat/webapps/

