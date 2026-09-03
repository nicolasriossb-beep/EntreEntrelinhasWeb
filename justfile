tomcat_arch := "/usr/share/tomcat10" # pacote tomcat10 do Arch
tomcat_download := "apache-tomcat-10.1.59/" # download
tomcat := tomcat_arch

deploy_arch := "/tmp/EntreEntrelinhasWeb"
deploy_download := "apache-tomcat-10.1.59/webapps/EntreEntrelinhasWeb/"
deploy := deploy_arch

# Depende do pacote netbeans do arch
copylibstask := "/usr/lib/netbeans/java/ant/extra/org-netbeans-modules-java-j2seproject-copylibstask.jar"

# Baixei esse arquivo em https://jdbc.postgresql.org/download/
postgre := "postgresql-42.7.13.jar"

build:
    ant dist \
        -Dj2ee.server.home={{tomcat}} \
        -Dlibs.CopyLibs.classpath={{copylibstask}} \
        -Dfile.reference.postgresql-42.7.13.jar={{postgre}}
    \rm -rf {{deploy_arch}}
    \cp -r build/web {{deploy_arch}}
    \rm -rf {{deploy_download}}
    \cp -r build/web {{deploy_download}}
